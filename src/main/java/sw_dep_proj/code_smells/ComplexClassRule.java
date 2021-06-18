package sw_dep_proj.code_smells;

import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.metrics.CKMetrics;

public class ComplexClassRule {

	public boolean isComplexClass(ClassBean pClass) {

		if(CKMetrics.getMcCabeMetric(pClass) > 200)
				return true;

		return false;
	}
}