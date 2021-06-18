package sw_dep_proj.code_smells;

import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.metrics.CKMetrics;

public class ClassDataShouldBePrivateRule {

	public boolean isClassDataShouldBePrivate(ClassBean pClass) {
		
		if(CKMetrics.getNOPA(pClass) > 10)
			return true;
		
		return false;
	}
}
