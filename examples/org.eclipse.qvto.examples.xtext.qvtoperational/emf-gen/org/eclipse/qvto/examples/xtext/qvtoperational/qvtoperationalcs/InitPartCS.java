/**
 */
package org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs;

import org.eclipse.ocl.examples.xtext.base.basecs.ElementCS;
import org.eclipse.ocl.examples.xtext.essentialocl.essentialoclcs.ExpCS;


/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Init Part CS</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitPartCS#getInitOp <em>Init Op</em>}</li>
 *   <li>{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitPartCS#getExpression <em>Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QVTOperationalCSPackage#getInitPartCS()
 * @model
 * @generated
 */
public interface InitPartCS
		extends ElementCS {

	/**
	 * Returns the value of the '<em><b>Init Op</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitOp}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Init Op</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Init Op</em>' attribute.
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitOp
	 * @see #setInitOp(InitOp)
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QVTOperationalCSPackage#getInitPartCS_InitOp()
	 * @model
	 * @generated
	 */
	InitOp getInitOp();

	/**
	 * Sets the value of the '
	 * {@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitPartCS#getInitOp
	 * <em>Init Op</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value
	 *            the new value of the '<em>Init Op</em>' attribute.
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitOp
	 * @see #getInitOp()
	 * @generated
	 */
	void setInitOp(InitOp value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(ExpCS)
	 * @see org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.QVTOperationalCSPackage#getInitPartCS_Expression()
	 * @model containment="true"
	 * @generated
	 */
	ExpCS getExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.qvto.examples.xtext.qvtoperational.qvtoperationalcs.InitPartCS#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(ExpCS value);

} // InitPartCS
