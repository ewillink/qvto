/*******************************************************************************
 * Copyright (c) 2009, 2013 R.Dvorak and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Radek Dvorak - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.internal.qvt.oml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.m2m.internal.qvt.oml.ast.env.InternalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.ModelParameterExtent;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEnvFactory;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalFileEnv;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalStdLibrary;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.compiler.CompiledUnit;
import org.eclipse.m2m.internal.qvt.oml.compiler.CompilerUtils;
import org.eclipse.m2m.internal.qvt.oml.compiler.QVTOCompiler;
import org.eclipse.m2m.internal.qvt.oml.compiler.UnitProxy;
import org.eclipse.m2m.internal.qvt.oml.compiler.UnitResolverFactory;
import org.eclipse.m2m.internal.qvt.oml.emf.util.EmfUtil;
import org.eclipse.m2m.internal.qvt.oml.evaluator.EvaluationMessages;
import org.eclipse.m2m.internal.qvt.oml.evaluator.InternalEvaluator;
import org.eclipse.m2m.internal.qvt.oml.evaluator.ModelInstance;
import org.eclipse.m2m.internal.qvt.oml.evaluator.ModelParameterHelper;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtException;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtInterruptedExecutionException;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtRuntimeException;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtStackOverFlowError;
import org.eclipse.m2m.internal.qvt.oml.expressions.ImperativeOperation;
import org.eclipse.m2m.internal.qvt.oml.expressions.ModelParameter;
import org.eclipse.m2m.internal.qvt.oml.expressions.Module;
import org.eclipse.m2m.internal.qvt.oml.expressions.OperationalTransformation;
import org.eclipse.m2m.internal.qvt.oml.library.Context;
import org.eclipse.m2m.internal.qvt.oml.trace.Trace;
import org.eclipse.m2m.qvt.oml.ExecutionContext;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.util.IContext;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * Internal transformation executor
 * 
 * @since 3.0
 */
public class InternalTransformationExecutor {

	private URI fURI;
	private EPackage.Registry fPackageRegistry;
	private CompiledUnit fCompiledUnit;
	private ResourceSet fCompilationRs;
	private ExecutionDiagnosticImpl fLoadDiagnostic;
	private OperationalTransformation fTransformation;
	private QvtOperationalEnvFactory fEnvFactory;

	/**
	 * Constructs the executor for the given transformation URI.
	 * <p>
	 * No attempt to resolve and load the transformation is done at this step
	 * 
	 * @param uri
	 *            the URI of an existing transformation
	 */
	public InternalTransformationExecutor(URI uri) {
		if (uri == null) {
			throw new IllegalArgumentException("null transformation URI"); //$NON-NLS-1$
		}

		fURI = uri;
	}
	
	public URI getURI() {
		return fURI;
	}
	
	public ResourceSet getResourceSet() {
		return fCompilationRs;
	}
		
	public InternalTransformationExecutor(URI uri, EPackage.Registry registry) {
		this(uri);
		
		if (registry == null) {
			throw new IllegalArgumentException("null package registry"); //$NON-NLS-1$
		}
		
		fPackageRegistry = registry;
	}	
			
	/**
	 * Attempts to load the transformation referred by this executor and checks
	 * if it is valid for execution.
	 * <p>
	 * <b>Remark:</b></br> Only the first performs the actual transformation
	 * loading, subsequent calls to this method will return the existing
	 * diagnostic.
	 * 
	 * @return the diagnostic indicating possible problems of the load action
	 */
	public Diagnostic loadTransformation() {
		if (fLoadDiagnostic == null) {
			doLoad();
		}
		return fLoadDiagnostic;
	}
	
	/**
	 * Retrieves compiled unit if the referencing URI gets successfully resolved
	 * <p>
	 * <b>Remark</b>: This method invocation causes the referenced transformation to
	 * load if not already done before by direct call to
	 * {@linkplain #loadTransformation()} or
	 * {@linkplain #execute(ExecutionContext, ModelExtent...)}
	 * 
	 * @return compiled unit or <code>null</code> if it failed to be obtained
	 */
	public CompiledUnit getUnit() {
		loadTransformation();
		return fCompiledUnit;
	}	

	/**
	 * Executes the transformation referred by this executor using the given
	 * model parameters and execution context.
	 * 
	 * @param executionContext
	 *            the context object keeping the execution environment details
	 * @param modelParameters
	 *            the actual model arguments to the transformation
	 * 
	 * @return the diagnostic object indicating the execution result status,
	 *         also keeping the details of possible problems
	 * @throws IllegalArgumentException
	 *             if the context or any of the model parameters is
	 *             <code>null</code>
	 */
	public ExecutionDiagnostic execute(ExecutionContext executionContext,
			ModelExtent... modelParameters) {
		// Java API check for nulls etc.
		if (executionContext == null) {
			throw new IllegalArgumentException();
		}
		checkLegalModelParams(modelParameters);

		// ensure transformation unit is loaded
		loadTransformation();

		// check if we have successfully loaded the transformation unit
		if (!isSuccess(fLoadDiagnostic)) {
			return fLoadDiagnostic;
		}

		try {
			return doExecute(modelParameters,
					createInternalContext(executionContext));
		} catch (QvtRuntimeException e) {
			Log logger = executionContext.getLog();
			logger.log(EvaluationMessages.TerminatingExecution);

			return createExecutionFailure(e);
		}
	}

	private ExecutionDiagnostic doExecute(ModelExtent[] args, IContext context) {
		QvtOperationalEnvFactory factory = getEnvironmentFactory();
		QvtOperationalEvaluationEnv evaluationEnv = factory
				.createEvaluationEnvironment(context, null);

		ExecutionDiagnostic modelParamsDiagnostic = initArguments(evaluationEnv, fTransformation, args);
		if (modelParamsDiagnostic.getSeverity() != Diagnostic.OK) {
			return modelParamsDiagnostic;
		}

		QvtOperationalFileEnv rootEnv = factory.createEnvironment(fCompiledUnit.getURI());
		EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> evaluator = factory
				.createEvaluationVisitor(rootEnv, evaluationEnv, null);

		// perform the actual execution
		assert evaluator instanceof InternalEvaluator : "expecting InternalEvaluator implementation"; //$NON-NLS-1$
		InternalEvaluator rawEvaluator = (InternalEvaluator) evaluator;

		rawEvaluator.execute(fTransformation);

		// unpack the internal extents into the passed model parameters
		List<Object> resultArgs = evaluationEnv.getOperationArgs();
		int i = 0;
		for (Object nextResultArg : resultArgs) {
			ModelInstance modelInstance = (ModelInstance) nextResultArg;
			ModelParameterExtent extent = modelInstance.getExtent();

			List<EObject> allRootElements = extent.getContents()
					.getAllRootElements();
			try {
				args[i++].setContents(allRootElements);
			} catch (UnsupportedOperationException e) {
				return new ExecutionDiagnosticImpl(Diagnostic.ERROR,
						ExecutionDiagnostic.MODEL_PARAMETER_MISMATCH, NLS
								.bind(Messages.ReadOnlyExtentModificationError,
										i - 1));
			}
		}

		// do some handy processing with traces
		Trace traces = evaluationEnv.getAdapter(InternalEvaluationEnv.class).getTraces();
		handleExecutionTraces(traces);
		
		return ExecutionDiagnostic.OK_INSTANCE;
	}
	
	protected void handleExecutionTraces(Trace traces) {
		// nothing interesting here
	}

	private void doLoad() {
		fLoadDiagnostic = ExecutionDiagnosticImpl.OK_INSTANCE;

		UnitProxy unit = UnitResolverFactory.Registry.INSTANCE.getUnit(fURI);
		if (unit == null) {
			fLoadDiagnostic = new ExecutionDiagnosticImpl(Diagnostic.ERROR,
					ExecutionDiagnostic.TRANSFORMATION_LOAD_FAILED, NLS.bind(
							Messages.UnitNotFoundError, fURI));
			return;
		}

		QVTOCompiler compiler = createCompiler();
		try {
			fCompiledUnit = compiler.compile(unit, null, null);
			fCompilationRs = compiler.getResourceSet();
		//	fCompilerKernel = compiler.getKernel();

			fLoadDiagnostic = createCompilationDiagnostic(fCompiledUnit);

		} catch (MdaException e) {
			fLoadDiagnostic = new ExecutionDiagnosticImpl(Diagnostic.ERROR,
					ExecutionDiagnostic.TRANSFORMATION_LOAD_FAILED, NLS.bind(
							Messages.FailedToCompileUnitError, fURI));

			fLoadDiagnostic.merge(BasicDiagnostic.toDiagnostic(e));
		}

		if (fCompiledUnit != null
				&& fLoadDiagnostic.getSeverity() == Diagnostic.OK) {
			fTransformation = getTransformation();
			if (fTransformation == null) {
				fLoadDiagnostic = new ExecutionDiagnosticImpl(Diagnostic.ERROR,
						ExecutionDiagnostic.TRANSFORMATION_LOAD_FAILED, NLS
								.bind(Messages.NotTransformationInUnitError,
										fURI));
				return;
			}

			ExecutionDiagnosticImpl validForExecution = checkIsExecutable(fTransformation);
			if (validForExecution.getSeverity() != Diagnostic.OK) {
				fLoadDiagnostic = validForExecution;
			}
		}
	}

	private ExecutionDiagnostic initArguments(
			QvtOperationalEvaluationEnv evalEnv,
			OperationalTransformation transformationModel, ModelExtent[] args) {

		EList<ModelParameter> modelParameters = transformationModel
				.getModelParameter();
		if (modelParameters.size() != args.length) {
			return new ExecutionDiagnosticImpl(Diagnostic.ERROR,
					ExecutionDiagnostic.MODEL_PARAMETER_MISMATCH, NLS.bind(
							Messages.InvalidModelParameterCountError,
							args.length, modelParameters.size()));
		}

		ExecutionDiagnostic result = ExecutionDiagnostic.OK_INSTANCE;
		List<ModelParameterExtent> extents = new ArrayList<ModelParameterExtent>(
				args.length);

		int argCount = 0;
		for (ModelParameter modelParam : modelParameters) {
			ModelParameterExtent nextExtent;
			ModelExtent nextArg = args[argCount++];

			if (modelParam.getKind() != org.eclipse.m2m.internal.qvt.oml.expressions.DirectionKind.OUT) {
				nextExtent = new ModelParameterExtent(nextArg.getContents(), getResourceSet(), modelParam);
			} else {
				nextExtent = new ModelParameterExtent(getResourceSet());
			}

			extents.add(nextExtent);
		}

		List<ModelInstance> modelArgs = ModelParameterHelper
				.createModelArguments(transformationModel, extents);
		evalEnv.getOperationArgs().addAll(modelArgs);

		return result;
	}

	private static ExecutionDiagnosticImpl checkIsExecutable(
			OperationalTransformation transformation) {
		EList<EOperation> operations = transformation.getEOperations();
		for (EOperation oper : operations) {
			if (oper instanceof ImperativeOperation
					&& QvtOperationalEnv.MAIN.equals(oper.getName())) {
				return ExecutionDiagnosticImpl.OK_INSTANCE;
			}
		}

		return new ExecutionDiagnosticImpl(Diagnostic.ERROR,
				ExecutionDiagnostic.VALIDATION, NLS.bind(
						Messages.NoTransformationEntryPointError,
						transformation.getName()));
	}

	public OperationalTransformation getTransformation() {
		// TODO - cached the transformation selected as main
		if(fCompiledUnit == null) {
			return null;
		}
		
		List<Module> allModules = fCompiledUnit.getModules();
		for (Module module : allModules) {
			if (module instanceof OperationalTransformation) {
				return (OperationalTransformation) module;
			}
		}

		return null;
	}
	
	public void setEnvironmentFactory(QvtOperationalEnvFactory factory) {
		fEnvFactory = factory;
	}

	protected QvtOperationalEnvFactory getEnvironmentFactory() {
		return fEnvFactory != null ? fEnvFactory : new QvtOperationalEnvFactory();
	}
	
	public void cleanup() {
		setEnvironmentFactory(null);
		if (fCompilationRs != null) {
			EmfUtil.cleanupResourceSet(fCompilationRs);
		}
	}


	private static ExecutionDiagnostic createExecutionFailure(
			QvtRuntimeException qvtRuntimeException) {
		int code = 0;
		int severity = Diagnostic.ERROR;
		String message = qvtRuntimeException.getLocalizedMessage();
		Object[] data = null;

		if (qvtRuntimeException instanceof QvtException) {
			code = ((QvtException) qvtRuntimeException).getExceptionType() == QvtOperationalStdLibrary.INSTANCE.getAssertionFailedClass() ?
					ExecutionDiagnostic.FATAL_ASSERTION : ExecutionDiagnostic.EXCEPTION_THROWN;
		} else if (qvtRuntimeException instanceof QvtInterruptedExecutionException) {
			code = ExecutionDiagnostic.USER_INTERRUPTED;
			severity = Diagnostic.CANCEL;
		} else {
			code = ExecutionDiagnostic.EXCEPTION_THROWN;
			if (qvtRuntimeException instanceof QvtStackOverFlowError == false) {
				Throwable cause = qvtRuntimeException.getCause();
				data = new Object[] { cause != null ? cause : qvtRuntimeException };
			} else {
				message = Messages.StackTraceOverFlowError;
			}
		}

		if (message == null) {
			message = NLS.bind(Messages.QVTRuntimeExceptionCaught,
					qvtRuntimeException.getClass().getName());
		}
		ExecutionDiagnosticImpl diagnostic = new ExecutionDiagnosticImpl(severity,
				code, message, data);
		diagnostic.setStackTrace(qvtRuntimeException.getQvtStackTrace());
		return diagnostic;
	}

	private void checkLegalModelParams(ModelExtent[] extents)
			throws IllegalArgumentException {
		if (extents == null) {
			throw new IllegalArgumentException("Null model parameters"); //$NON-NLS-1$
		}

		for (int i = 0; i < extents.length; i++) {
			if (extents[i] == null) {
				throw new IllegalArgumentException(
						"Null model parameter[" + i + "]"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
	}

	private static ExecutionDiagnosticImpl createCompilationDiagnostic(
			CompiledUnit compiledUnit) {
		List<QvtMessage> errors = compiledUnit.getErrors();
		if (errors.isEmpty()) {
			return ExecutionDiagnosticImpl.OK_INSTANCE;
		}

		URI uri = compiledUnit.getURI();
		ExecutionDiagnosticImpl mainDiagnostic = new ExecutionDiagnosticImpl(
				Diagnostic.ERROR, ExecutionDiagnostic.VALIDATION, NLS.bind(
						Messages.CompilationErrorsFoundInUnit, uri.toString()));

		for (QvtMessage message : errors) {
			// FIXME - we should include warnings as well
			mainDiagnostic.add(CompilerUtils.createProblemDiagnostic(uri, message));
		}

		return mainDiagnostic;
	}

	private static boolean isSuccess(Diagnostic diagnostic) {
		int severity = diagnostic.getSeverity();
		return severity == Diagnostic.OK || severity == Diagnostic.WARNING
				|| severity == Diagnostic.INFO;
	}

	private static IContext createInternalContext(
			ExecutionContext executionContext) {
		Context ctx = new Context();
		ctx.setLog(executionContext.getLog());
		ctx.setMonitor(executionContext.getMonitor());

		for (String key : executionContext.getConfigPropertyNames()) {
			Object value = executionContext.getConfigProperty(key);
			ctx.setConfigProperty(key, value);
		}

		return ctx;
	}

	@Override
	public String toString() {
		return "QVTO-Executor: " + fURI; //$NON-NLS-1$
	}
	
	private QVTOCompiler createCompiler() {
		if(fPackageRegistry == null) {
			return CompilerUtils.createCompiler();
		}
		
		return QVTOCompiler.createCompiler(fPackageRegistry);
	}

}
