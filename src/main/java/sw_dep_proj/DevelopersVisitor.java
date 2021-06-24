package sw_dep_proj;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CommitVisitor;
import org.repodriller.scm.RepositoryFile;
import org.repodriller.scm.SCMRepository;

import sw_dep_proj.beans.ClassBean;
import sw_dep_proj.beans.PackageBean;
import sw_dep_proj.metrics.CKMetrics;
import sw_dep_proj.metrics.FileUtility;
import sw_dep_proj.parser.ClassParser;
import sw_dep_proj.parser.CodeParser;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

// This class represents a RepoDriller Processor
// Implement CommitVisitor interface. 
// Inside of process(), we print the commit hash and the name of the developer.

public class DevelopersVisitor implements CommitVisitor {

    boolean fal = false;

    @Override
    public void process(SCMRepository scmRepository, Commit commit, PersistenceMechanism writer) {
        // We will need a parser to mine the structural properties of a class.
        CodeParser codeParser = new CodeParser();
        if(!fal){
            writer.write("Commit Hash", "Class Name", "LOC", "LCOM", "CBO", "ELOC", "COH", "McCabe", "TCC", "LCC", "HALSTAD", "DIT", "NOC", "RFC", "MESSAGE");
            fal = true;
        }
        // We want to compute metrics for all classes of the repository.
        List<RepositoryFile> files = scmRepository.getScm().files();

        int iterations = 0;
        int meanLoc = 0, meanLcom = 0, meanCbo = 0, meanEloc = 0, meanCoh = 0, meanWmc = 0, meanTcc = 0, meanLcc = 0, meanHalstead = 0, meanDit = 0, meanNoc = 0, meanRfc = 0;
        int LOC = 0, LCOM = 0, CBO = 0, ELOC = 0, COH = 0, WMC = 0, TCC = 0, LCC = 0, intHalstead = 0, DIT = 0, NOC = 0, RFC = 0;
        String className = "";
        for (RepositoryFile file : files) { // So, for each file available in the repository,
        iterations++;
            if (file.getFullName().contains(".java")) { // we check if the file is a java file, i.e., we do not want to consider configuration files and others.

                // Afterwards, we start parsing the file.
                CompilationUnit parsed;
                try {
                    //System.out.println("ciao sono qui");
                    // We exploit the parser capabilities to read a file and transform it in something usable for analysis.
                    parsed = codeParser.createParser(FileUtility.readFile(file.getFile().getAbsolutePath()));
                    // Just for your information, the following instruction means that we will
                    // analyze only the main class in a file, i.e., we DO NOT consider inner classes.
                    TypeDeclaration typeDeclaration = (TypeDeclaration) parsed.types().get(0);
                    if(typeDeclaration.getTypes().length == 0){
                        System.out.println("No main class in this file, continuing ...");
                    }
                    // While we could already use the TypeDeclaration to compute metrics, for the sake of
                    // understandability we are going to create more easily comprehensible objects, i.e.,
                    // those defined in the package 'beans'

                    Vector<String> imports = new Vector<String>();
                    for (Object importedResource : parsed.imports()) {
                        imports.add(importedResource.toString());
                    }

                    PackageBean packageBean = new PackageBean();
                    packageBean.setName(parsed.getPackage().getName().getFullyQualifiedName());

                    ClassBean classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                    classBean.setPathToClass(file.getFile().getAbsolutePath());

                    Vector<ClassBean> system = new Vector<>();
                    system.add(classBean);

                    // Once converted the files read in our beans, we can compute metrics in a super-easy way.
                    // NB: The class 'CKMetrics' contains a number of other metrics!
                    LOC = CKMetrics.getLOC(classBean); // high is bad
                    meanLoc += LOC;
                    LCOM = CKMetrics.getLCOM2(classBean); //  A low value of LCOM2 or LCOM3 indicates high cohesion and a well-designed class, A higher value of LCOM2 or LCOM3 indicates decreased encapsulation and increased complexity, thereby increasing the likelihood of errors
                    meanLcom += LCOM;
                    CBO = CKMetrics.getCBO(classBean); //  large values are good and low values are bad
                    meanCbo += CBO;

                    ELOC = CKMetrics.getELOC(classBean); // high is bad
                    meanEloc += ELOC;
                    COH = CKMetrics.getCoh(classBean); // low is bad
                    meanCoh += COH;
                    WMC = CKMetrics.getWMC(classBean); // high is bad
                    meanWmc += WMC;

                    TCC = CKMetrics.getTCC(classBean); // TCC < 0.5 and LCC < 0.5 are considered non-cohesive classes.
                    meanTcc += TCC;
                    LCC = CKMetrics.getLCC(classBean); // LCC = 0.8 is considered "quite cohesive". TCC=LCC=1 is a maximally cohesive class: all methods are connected. LCC tells the overall connectedness
                    meanLcc += LCC;
                    double HALSTEAD = CKMetrics.getHalsteadVolume(classBean); // high is bad
                    intHalstead = (int) HALSTEAD;
                    meanHalstead += intHalstead;

                    // The Depth of Inheritance Tree (DIT) metric provides for each class a measure of the inheritance levels from the object hierarchy top.
                    // The minimum value of DIT for a class is 1.
                    // A DIT value of 0 indicates a root while a value of 2 and 3 indicates a higher degree of reuse.
                    // If there is a majority of DIT values below 2, it may represent poor exploitation of the advantages of OO design and inheritance
                    DIT = CKMetrics.getDIT(classBean, system, 0); // low is bad -
                    meanDit += DIT;
                    NOC = CKMetrics.getNOC(classBean, system); // high is bad - https://www.coursehero.com/file/p39im9tk/Number-of-children-NOC-metric-For-a-given-class-NOC-is-defined-as-the-number-of/
                    meanNoc += NOC;
                    RFC = CKMetrics.getRFC(classBean); // high is bad
                    meanRfc += RFC;

                    className = classBean.getName();
                    // Now, let's pretty-print our results.
                    writer.write(
                            commit.getHash().replace("\n","").replace(",", ""),
                            classBean.getName(),
                            LOC,
                            LCOM,
                            CBO,
                            ELOC,
                            COH,
                            WMC,
                            TCC,
                            LCC,
                            intHalstead,
                            DIT,
                            NOC,
                            RFC,
                            commit.getMsg()
                    );

                } catch (IOException ioe){
                    System.out.println("Reading failed");
                }
                catch (IndexOutOfBoundsException iob){
                    System.out.println("IOB caught, continuing with another file");
                }
            }
        }
        meanLoc = meanLoc / iterations;
        meanLcom = meanLcom / iterations;
        meanCbo = meanCbo / iterations;
        meanEloc = meanEloc / iterations;
        meanCoh = meanCoh / iterations;
        meanWmc = meanWmc / iterations;
        meanTcc = meanTcc / iterations;
        meanLcc = meanLcc / iterations;
        meanHalstead = meanHalstead / iterations;
        meanDit = meanDit / iterations;
        meanNoc = meanNoc / iterations;
        meanRfc = meanRfc / iterations;
        System.out.println( "mean Loc is: " + meanLoc + "\n" +
                            "mean Lcom is: " + meanLcom + "\n" +
                            "mean CBO is: " + meanCbo + "\n" +
                            "mean Eloc is: " + meanEloc + "\n" +
                            "mean Coh is: " + meanCoh + "\n" +
                            "mean Wmc is: " + meanWmc + "\n" +
                            "mean Tcc is: " + meanTcc + "\n" +
                            "mean Lcc is: " + meanLcc + "\n" +
                            "mean Halstead is: " + meanHalstead + "\n" +
                            "mean Dit is: " + meanDit + "\n" +
                            "mean Noc is: " + meanNoc + "\n" +
                            "mean Rfc is: " + meanRfc);
        impactingMetricsCsv(
                className,
                locRule(LOC, meanLoc), lcomRule(LCOM, meanLcom), cboRule(CBO, meanCbo),
                elocRule(ELOC, meanEloc), cohRule(COH, meanCoh), wmcRule(WMC, meanWmc),
                tccRule(TCC, meanTcc), lccRule(LCC, meanLcc), halsteadRule(intHalstead, meanHalstead),
                ditRule(DIT, meanDit), nocRule(NOC, meanNoc), rfcRule(RFC, meanRfc)
        );
    }

    // we need to set range in order to evaluate maintainability
    /*
    * LOC >= 200
    * LCOM >=
    * CBO <=
    * ELOC >= 150
    * COH <=
    * McCabe >=
    * TCC <= 0.5
    * LCC >= 0.6
    * Halstead >= 1000
    * DIT < 1
    * NOC > 2
    * RFC >= 50
    * */
    public int locRule(int loc, int mLoc){
        return (loc >= mLoc) ? 1:0; // loc >= meanLoc is bad
    }

    public int lcomRule(int lcom, int mlcom){
        return (lcom >= mlcom) ? 1:0; // lcom >= meanLoc is bad
    }

    public int cboRule(int cbo, int mcbo){
        return (cbo <= mcbo) ? 1:0; // cbo <= meanCbo is bad
    }

    public int elocRule(int eloc, int meloc){
        return (eloc >= meloc) ? 1:0;
    }

    public int cohRule(int coh, int mcoh){
        return (coh <= mcoh) ? 1:0;
    }

    public int wmcRule(int wmc, int mwmc){
        return (wmc >= mwmc) ? 1:0;
    }

    public int tccRule(int tcc, int mtcc){
        return (tcc <= mtcc) ? 1:0;
    }

    public int lccRule(int lcc, int mlcc){
        return (lcc >= mlcc) ? 1:0;
    }

    public int halsteadRule(int halstead, int mhalstead){
        return (halstead >= mhalstead) ? 1:0;
    }

    public int ditRule(int dit, int mdit){
        return (dit < mdit) ? 1:0;
    }

    public int nocRule(int noc, int mnoc){
        return (noc > mnoc) ? 1:0;
    }

    public int rfcRule(int rfc, int mrfc){
        return (rfc >= mrfc) ? 1:0;
    }

    // scegli quale di questi vale 0.5, se sta roba vale + di 6, per
    public void impactingMetricsCsv(String className, int locRule, int lcomRule, int cboRule, int elocRule, int cohRule, int wmcRule, int tccRule, int lccRule, int halRule, int ditRule, int nocRule, int rfcRule){
        CSVFile meanCsv = new CSVFile("impactingMetricValues.csv");
        meanCsv.write("CLASS","LOC", "LCOM", "CBO", "ELOC", "COH", "WMC", "TCC", "LCC", "Halstead", "DIT", "NOC", "RFC", "MAINTAINABILITY");
        meanCsv.write(className, locRule, lcomRule, cboRule, elocRule, cohRule, wmcRule, tccRule, lccRule, halRule, ditRule, nocRule, rfcRule);

        meanCsv.close(); // questo è il real-dataset
    }
}


