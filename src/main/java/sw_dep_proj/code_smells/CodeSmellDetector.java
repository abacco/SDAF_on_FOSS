package sw_dep_proj.code_smells;

import org.eclipse.core.runtime.CoreException;
import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.beans.MethodBean;
import sw_dep_proj.beans.PackageBean;
import sw_dep_proj.code_smells.utilities.FolderToJavaProjectConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

public class CodeSmellDetector {

    public static void main(String args[]) {

        detectThread("zuul");
        detectThread("azure-sdk-for-java");
        detectThread("shopizer");
        detectThread("graal");
    }

    public static void detectThread(String projectName){

        Thread innerThread = new Thread(() -> {
            try {
                detectSmell(projectName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        innerThread.start();
    }

    public static void detectSmell(String projectName) throws FileNotFoundException {
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

        LongParameterListSmell lpls = new LongParameterListSmell();

        // The following rules are quite low for detecting smelly code components.
        // MisplacedClassRule misplacedClass = new MisplacedClassRule();
        // FeatureEnvyRule featureEnvy = new FeatureEnvyRule();

        PrintWriter printWriter = new PrintWriter(projectName.concat("Smells.txt"));
        
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

                        for(MethodBean methodBean: classBean.getMethods()) {
                            boolean isLongMethod = longMethod.isLongMethod(methodBean);
                            boolean hasLongParameterList = lpls.hasLongParamMethodList(methodBean);

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
        printWriter.close();
    }
}