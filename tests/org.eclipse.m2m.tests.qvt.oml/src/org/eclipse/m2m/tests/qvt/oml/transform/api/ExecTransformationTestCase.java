/*******************************************************************************
 * Copyright (c) 2007 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Borland Software Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.m2m.tests.qvt.oml.transform.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.m2m.internal.qvt.oml.common.MDAConstants;
import org.eclipse.m2m.internal.qvt.oml.emf.util.EmfUtil;
import org.eclipse.m2m.qvt.oml.runtime.util.QvtoTransformationHelper;
import org.eclipse.m2m.qvt.oml.runtime.util.QvtoTransformationHelper.ModelExtent;
import org.eclipse.m2m.qvt.oml.runtime.util.QvtoTransformationHelper.TransfExecutionResult;
import org.eclipse.m2m.tests.qvt.oml.transform.ModelTestData;

/**
 * @author sboyko
 */
public class ExecTransformationTestCase extends ApiTestCase {
	
    public ExecTransformationTestCase(ModelTestData data) {
        super(data);
        setName("workspace: " + data.getName()); //$NON-NLS-1$
    }
    
    @Override
    public void runTest() throws Exception {
    	URI scriptUri = createScriptUri(getData().getName());
    	
		List<EObject> inObjects = new ArrayList<EObject>();
		List<URI> inputs = getData().getIn(getProject());
		for (URI uri : inputs) {
			inObjects.add(EmfUtil.loadModel(uri));
		}
		TransfExecutionResult execResult = new QvtoTransformationHelper(scriptUri).executeTransformation(inObjects, Collections.<String, Object>emptyMap(), null);
		
		assertNotNull("Non-null trace expected", execResult.getTrace()); //$NON-NLS-1$
		
		Iterator<ModelExtent> itrExt = execResult.getOutModelExtents().iterator();
		for (URI uri : getData().getExpected(getProject())) {
			if (!itrExt.hasNext()) {
				throw new Exception("Missed execution result model extent"); //$NON-NLS-1$
			}
			Resource loadResource = EmfUtil.loadResource(uri);
			ModelExtent nextExtent = itrExt.next();
			for (int i = 0; i < loadResource.getContents().size(); ++i) {
				ModelTestData.assertEquals("Diff execution result", loadResource.getContents().get(i), nextExtent.getAllRootElements().get(i)); //$NON-NLS-1$
			}
		}

		if (execResult.getOutParameters().isEmpty()) {
			return;
		}
		
		Iterator<EObject> itrObj = execResult.getOutParameters().iterator();
		for (URI uri : getData().getExpected(getProject())) {
			if (!itrObj.hasNext()) {
				throw new Exception("Missed execution result out parameter"); //$NON-NLS-1$
			}
			Resource loadResource = EmfUtil.loadResource(uri);
			ModelTestData.assertEquals("Diff execution result", loadResource.getContents().get(0), itrObj.next()); //$NON-NLS-1$
		}
    }
    
	protected URI createScriptUri(String scriptName) {
		IFile qvtoFile = getIFile(getData().getName() + MDAConstants.QVTO_FILE_EXTENSION_WITH_DOT);
		return URI.createPlatformResourceURI(qvtoFile.getFullPath().toString(), false);
	}

}
