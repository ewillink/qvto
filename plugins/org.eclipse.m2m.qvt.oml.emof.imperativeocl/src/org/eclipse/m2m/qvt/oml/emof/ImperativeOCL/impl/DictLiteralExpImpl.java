/**
 * <copyright>
 * </copyright>
 *
 * $Id: DictLiteralExpImpl.java,v 1.2 2009/01/25 23:11:18 radvorak Exp $
 */
package org.eclipse.m2m.qvt.oml.emof.ImperativeOCL.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.m2m.qvt.oml.emof.ImperativeOCL.DictLiteralExp;
import org.eclipse.m2m.qvt.oml.emof.ImperativeOCL.DictLiteralPart;
import org.eclipse.m2m.qvt.oml.emof.ImperativeOCL.ImperativeOCLPackage;
import org.eclipse.qvt.declarative.emof.EssentialOCL.impl.LiteralExpImpl;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Dict Literal Exp</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.m2m.qvt.oml.emof.ImperativeOCL.impl.DictLiteralExpImpl#getPart <em>Part</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DictLiteralExpImpl extends LiteralExpImpl implements DictLiteralExp {
	/**
	 * The cached value of the '{@link #getPart() <em>Part</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPart()
	 * @generated
	 * @ordered
	 */
	protected EList<DictLiteralPart> part;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DictLiteralExpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ImperativeOCLPackage.Literals.DICT_LITERAL_EXP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DictLiteralPart> getPart() {
		if (part == null) {
			part = new EObjectContainmentEList<DictLiteralPart>(DictLiteralPart.class, this, ImperativeOCLPackage.DICT_LITERAL_EXP__PART);
		}
		return part;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ImperativeOCLPackage.DICT_LITERAL_EXP__PART:
				return ((InternalEList<?>)getPart()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ImperativeOCLPackage.DICT_LITERAL_EXP__PART:
				return getPart();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ImperativeOCLPackage.DICT_LITERAL_EXP__PART:
				getPart().clear();
				getPart().addAll((Collection<? extends DictLiteralPart>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ImperativeOCLPackage.DICT_LITERAL_EXP__PART:
				getPart().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ImperativeOCLPackage.DICT_LITERAL_EXP__PART:
				return part != null && !part.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DictLiteralExpImpl
