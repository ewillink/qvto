/**
 * <copyright>
 * Copyright (c) 2013 Willink Transformations, University of York, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   E.D.Willink - Initial API and implementation
 *   Adolfo Sanchez-Barbudo (University of York) - Bug397429
 * </copyright>
 */
package org.eclipse.qvto.examples.pivot.qvtoperational;

import org.eclipse.ocl.examples.pivot.CallExp;
import org.eclipse.ocl.examples.pivot.OCLExpression;
import org.eclipse.ocl.examples.pivot.Variable;

import org.eclipse.ocl.examples.pivot.util.Visitor;
import org.eclipse.qvto.examples.pivot.imperativeocl.ImperativeExpression;
import org.eclipse.qvto.examples.pivot.qvtoperational.util.QVTOperationalVisitor;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resolve Exp</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#getCondition <em>Condition</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isIsDeferred <em>Is Deferred</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isIsInverse <em>Is Inverse</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isOne <em>One</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp()
 * @model
 * @generated
 */
public interface ResolveExp extends CallExp, ImperativeExpression {
	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(OCLExpression)
	 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp_Condition()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getCondition();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Is Deferred</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Deferred</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Deferred</em>' attribute.
	 * @see #setIsDeferred(boolean)
	 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp_IsDeferred()
	 * @model dataType="org.eclipse.ocl.examples.pivot.Boolean"
	 * @generated
	 */
	boolean isIsDeferred();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isIsDeferred <em>Is Deferred</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Deferred</em>' attribute.
	 * @see #isIsDeferred()
	 * @generated
	 */
	void setIsDeferred(boolean value);

	/**
	 * Returns the value of the '<em><b>Is Inverse</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Inverse</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Inverse</em>' attribute.
	 * @see #setIsInverse(boolean)
	 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp_IsInverse()
	 * @model dataType="org.eclipse.ocl.examples.pivot.Boolean"
	 * @generated
	 */
	boolean isIsInverse();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isIsInverse <em>Is Inverse</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Inverse</em>' attribute.
	 * @see #isIsInverse()
	 * @generated
	 */
	void setIsInverse(boolean value);

	/**
	 * Returns the value of the '<em><b>One</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>One</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>One</em>' attribute.
	 * @see #setOne(boolean)
	 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp_One()
	 * @model dataType="org.eclipse.ocl.examples.pivot.Boolean"
	 * @generated
	 */
	boolean isOne();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#isOne <em>One</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>One</em>' attribute.
	 * @see #isOne()
	 * @generated
	 */
	void setOne(boolean value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Variable)
	 * @see org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalPackage#getResolveExp_Target()
	 * @model containment="true"
	 * @generated
	 */
	Variable getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.pivot.qvtoperational.ResolveExp#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Variable value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vType="org.eclipse.qvto.examples.pivot.qvtoperational.QVTOperationalVisitor<R>"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return v.visitResolveExp(this);'"
	 * @generated
	 */
	<R> R accept(QVTOperationalVisitor<R> v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vType="org.eclipse.ocl.examples.pivot.Visitor<R>"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='return ((QVTOperationalVisitor<R>)v).visitResolveExp(this);'"
	 * @generated
	 */
	<R> R accept(Visitor<R> v);

} // ResolveExp
