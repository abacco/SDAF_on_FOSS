package sw_dep_proj.code_smells;


import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.metrics.CKMetrics;

public class FunctionalDecompositionRule {

	public boolean isFunctionalDecomposition(ClassBean pClass) {
		int nPrivateFields = CKMetrics.getNOPrivateA(pClass);
		int noa = CKMetrics.getNOA(pClass);

		if(noa == nPrivateFields) {
			if(CKMetrics.getWMC(pClass) < 3) {
				String className = pClass.getName();

				if( (className.toLowerCase().contains("make")) || (className.toLowerCase().contains("create")) ||
						(className.toLowerCase().contains("creator")) || (className.toLowerCase().contains("execute")) ||
						(className.toLowerCase().contains("exec")) || (className.toLowerCase().contains("compute")) || 
						(className.toLowerCase().contains("display")) || (className.toLowerCase().contains("calculate"))) {

					return true;
				}

			}
		}

		return false;
	}
}
