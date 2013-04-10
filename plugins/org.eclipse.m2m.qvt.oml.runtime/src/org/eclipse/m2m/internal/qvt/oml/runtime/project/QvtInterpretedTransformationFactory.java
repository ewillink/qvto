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
package org.eclipse.m2m.internal.qvt.oml.runtime.project;

import org.eclipse.emf.common.util.URI;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;


public class QvtInterpretedTransformationFactory implements QvtTransformationInterpreterFactory {
	
	public QvtInterpretedTransformationFactory() {
		super();
	}
	
	public QvtTransformation create(String moduleID) throws MdaException {	
		return new QvtInterpretedTransformation(new DeployedQvtModule(URI.createURI(moduleID)));
	}
}
