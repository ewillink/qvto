package org.eclipse.qvto.examples.xtext.imperativeocl.cs2as;

import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.ocl.examples.xtext.base.cs2pivot.CS2PivotConversion;
import org.eclipse.ocl.examples.xtext.essentialocl.cs2pivot.EssentialOCLCS2Pivot;

public class ImperativeOCLCS2AS extends EssentialOCLCS2Pivot {
	
	public ImperativeOCLCS2AS(@NonNull Map<? extends Resource, ? extends Resource> cs2pivotResourceMap, @NonNull MetaModelManager metaModelManager) {
		super(cs2pivotResourceMap, metaModelManager);
	}
		
	public ImperativeOCLCS2AS(ImperativeOCLCS2AS cs2pivot) {
		super(cs2pivot);
	}

	@Override
	protected @NonNull
	ImperativeOCLContainmentVisitor createContainmentVisitor(
			@NonNull CS2PivotConversion converter) {
		return new ImperativeOCLContainmentVisitor(converter);
	}
	
	@Override
	protected @NonNull
	ImperativeOCLPreOrderVisitor createPreOrderVisitor(
			@NonNull CS2PivotConversion converter) {
	
		return new ImperativeOCLPreOrderVisitor(converter);
	}
	
	@Override
	protected @NonNull
	ImperativeOCLPostOrderVisitor createPostOrderVisitor(
			@NonNull CS2PivotConversion converter) {
		return new ImperativeOCLPostOrderVisitor(converter);
	}

	@Override
	protected @NonNull
	ImperativeOCLLeft2RightVisitor createLeft2RightVisitor(
			@NonNull CS2PivotConversion converter) {
		return new ImperativeOCLLeft2RightVisitor(converter);
	}
}
