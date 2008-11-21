/*******************************************************************************
 * Copyright (c) 2008 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2m.internal.qvt.oml.stdlib;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.m2m.internal.qvt.oml.ast.env.QvtOperationalEvaluationEnv;
import org.eclipse.m2m.internal.qvt.oml.evaluator.ModuleInstance;
import org.eclipse.m2m.internal.qvt.oml.evaluator.QvtRuntimeException;

public class TransformationOperations extends AbstractContextualOperations {

    public TransformationOperations(AbstractQVTStdlib library) {
		super(library, library.getTransformationClass());
	}
	
	@Override
	protected OperationProvider[] getOperations() {
		return new OwnedOperationProvider[] {
				new OwnedOperationProvider(createTransformHandler(getStdlib()), "transform", getStdlib().getStatusClass())//$NON-NLS-1$ 
		};
	}
	
	private static CallHandler createTransformHandler(final AbstractQVTStdlib stdlib) {
		return new CallHandler() {
			public Object invoke(ModuleInstance module, Object source, Object[] args, QvtOperationalEvaluationEnv evalEnv) {
				// FIXME - add validation code!!!
				ModuleInstance moduleInstance = (ModuleInstance) source;
				CallHandler mainHandler = moduleInstance.getAdapter(ModuleInstance.Internal.class).getEntryOperationHandler();
				if(mainHandler == null) {
					// module has no main operation => return invalid
					return CallHandlerAdapter.getInvalidResult(evalEnv);
				}
				
			    try {
			    	mainHandler.invoke(moduleInstance, source, args, evalEnv);
			    } catch(QvtRuntimeException e) {
			    	// TODO - support raised exception
			    	EClass raisedException = null;
			    	return StatusOperations.createFailed(stdlib.getStatusClass(), raisedException);
			    }
			    return StatusOperations.createSuccess(stdlib.getStatusClass());
			}
		};
	}
}
