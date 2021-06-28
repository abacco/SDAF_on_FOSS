package sw_dep_proj.code_smells;

import com.opencsv.CSVWriter;
import org.eclipse.core.runtime.CoreException;
import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.beans.MethodBean;
import sw_dep_proj.beans.PackageBean;
import sw_dep_proj.code_smells.utilities.FolderToJavaProjectConverter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class CodeSmellDetector {

    public static void main(String args[]) throws IOException {

/*        detectSmell("zuul");
        detectSmell("azure-sdk-for-java");
        detectSmell("shopizer");*/
        detectSmell("graal");
    }

    private static boolean fal = false;

    public static void detectThread(String projectName){

        Thread innerThread = new Thread(() -> {
            try {
                detectSmell(projectName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        innerThread.start();
    }

    public static void detectSmell(String projectName) throws IOException {
        // Path to the directory containing all the projects under analysis
        String pathToDirectory = "C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\" + projectName;
        File experimentDirectory = new File(pathToDirectory);

        // Declaring Class-level code smell objects.
        ClassDataShouldBePrivateRule cdsbp = new ClassDataShouldBePrivateRule();
        ComplexClassRule complexClass = new ComplexClassRule();
        FunctionalDecompositionRule functionalDecomposition = new FunctionalDecompositionRule();
        GodClassRule godClass = new GodClassRule();
        SpaghettiCodeRule spaghettiCode = new SpaghettiCodeRule();
        LongMethodRule longMethod = new LongMethodRule();
        FeatureEnvyRule fev = new FeatureEnvyRule();
        LongParameterListSmell lpls = new LongParameterListSmell();

        // The following rules are quite low for detecting smelly code components.

        PrintWriter printWriter = new PrintWriter(projectName.concat("Smells.txt"));

        CSVWriter c = new CSVWriter(new FileWriter("src/main/java/sw_dep_proj/final_data/"+ projectName.concat("Output_smell_detector.csv")), ',', CSVWriter.NO_QUOTE_CHARACTER);
        String[] smells_name = {
                "ClassDataShouldBePrivate",
                "ComplexClass",
                "FunctionalDecomposition",
                "GodClass",
                "SpaghettiCode",
                "MAINTAINABILITY"
        };

        if(!fal) {
            c.writeNext(smells_name);
            fal = true;
        }
        
        for(File project: experimentDirectory.listFiles()) {
            try {
                // Method to convert a directory into a set of java packages.
                Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());

                for(PackageBean packageBean: packages) {
                    for(ClassBean classBean: packageBean.getClasses()) {

                        // How to call methods for computing code smell detection.
                        // The currently implemented detector is DECOR (http://www.irisa.fr/triskell/publis/2009/Moha09d.pdf)
                        boolean isClassDataShouldBePrivate = cdsbp.isClassDataShouldBePrivate(classBean);
                        boolean isComplexClass = complexClass.isComplexClass(classBean);
                        boolean isFunctionalDecomposition = functionalDecomposition.isFunctionalDecomposition(classBean);
                        boolean isGodClass = godClass.isGodClass(classBean);
                        boolean isSpaghettiCode = spaghettiCode.isSpaghettiCode(classBean);

                        int classDataPrivate_int = (isClassDataShouldBePrivate) ? 1:0;
                        int complexClass_int = (isComplexClass) ? 1:0;
                        int functionalDecomposition_int = (isFunctionalDecomposition) ? 1:0;
                        int godClass_int = (isGodClass) ? 1:0;
                        int spaghCode = (isSpaghettiCode) ? 1:0;

                        int maintainability_value = classDataPrivate_int + complexClass_int + functionalDecomposition_int +
                                godClass_int + spaghCode;

                        String maintainability_str;

                        if(maintainability_value >= 1){ // one or more smell detected
                            maintainability_str = "FALSE";
                        }
                        else{
                            maintainability_str = "TRUE";
                        }

                        String[] booleans = {
                                String.valueOf(classDataPrivate_int),
                                String.valueOf(complexClass_int),
                                String.valueOf(functionalDecomposition_int),
                                String.valueOf(godClass_int),
                                String.valueOf(spaghCode),
                                maintainability_str
                        };

                        c.writeNext(booleans);

                        System.out.println("Class: " + classBean.getBelongingPackage()
                                + "." + classBean.getName() + "\n"
                                + "		ClassDataShouldBePrivate: " + isClassDataShouldBePrivate + "\n"
                                + "		ComplexClass: " + isComplexClass + "\n"
                                + "		FunctionalDecomposition: " + isFunctionalDecomposition + "\n"
                                + "		GodClass: " + isGodClass + "\n"
                                + "		SpaghettiCode: " + isSpaghettiCode);

                        printWriter.print(classBean.getBelongingPackage() + " " + classBean.getName() + "\n"
                                + "		ClassDataShouldBePrivate: " + isClassDataShouldBePrivate + "\n"
                                + "		ComplexClass: " + isComplexClass + "\n"
                                + "		FunctionalDecomposition: " + isFunctionalDecomposition + "\n"
                                + "		GodClass: " + isGodClass + "\n"
                                + "		SpaghettiCode: " + isSpaghettiCode);

                        boolean isLongMethod, hasLongParameterList;
                        ArrayList<String> booleans2 = new ArrayList<>();
                        for(MethodBean methodBean: classBean.getMethods()) {
                            isLongMethod = longMethod.isLongMethod(methodBean);
                            hasLongParameterList = lpls.hasLongParamMethodList(methodBean);

                            System.out.println("Method: " + methodBean.getName() + "\n"
                                    + "     hasLongParameterList: " + hasLongParameterList + "\n"
                                    + "     LongMethod: " + isLongMethod);

                            printWriter.print("Method: " + methodBean.getName() + "\n"
                                    + "     hasLongParameterList: " + hasLongParameterList + "\n"
                                    + "     LongMethod: " + isLongMethod + "\n");

                        }
                    }
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        c.close();
        printWriter.close();
    }
}