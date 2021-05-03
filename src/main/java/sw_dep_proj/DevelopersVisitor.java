package sw_dep_proj;


import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.repodriller.domain.Commit;
import org.repodriller.persistence.PersistenceMechanism;
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
public class DevelopersVisitor implements CommitVisitor{

    boolean fal = false;
    // write all the useful info to gather data
    @Override
    public void process(SCMRepository repo, Commit commit, PersistenceMechanism writer) {
    // We will need a parser to mine the structural properties of a class.
        CodeParser codeParser = new CodeParser();

        // We want to compute metrics for all classes of the repository.
        List<RepositoryFile> files = repo.getScm().files();
        if(!fal) {
            writer.write("LOC", "LCOM", "CBO", "ELOC", "COH", "WMC", "MESSAGE", "DATE","FILENAME","CHANGE TYPE");
            fal = true;
        }
        for (RepositoryFile file: files) { // So, for each file available in the repository,
            if (file.getFullName().contains(".java")) { // we check if the file is a java file, i.e., we do not want to consider configuration files and others.

                // Afterwards, we start parsing the file.
                CompilationUnit parsed;
                try {
                    // We exploit the parser capabilities to read a file and transform it in something usable for analysis.
                    parsed = codeParser.createParser(FileUtility.readFile(file.getFile().getAbsolutePath()));
                    // Just for your information, the following instruction means that we will
                    // analyze only the main class in a file, i.e., we DO NOT consider inner classes.
                    TypeDeclaration typeDeclaration = (TypeDeclaration) parsed.types().get(0);

                    // While we could already use the TypeDeclaration to compute metrics, for the sake of
                    // understandability we are going to create more easily comprehensible objects, i.e.,
                    // those defined in the package 'beans'
                    Vector<String> imports = new Vector<String>();
                    for (Object importedResource : parsed.imports())
                        imports.add(importedResource.toString());

                    PackageBean packageBean = new PackageBean();
                    packageBean.setName(parsed.getPackage().getName().getFullyQualifiedName());


                    ClassBean classBean = ClassParser.parse(typeDeclaration, packageBean.getName(), imports);
                    classBean.setPathToClass(file.getFile().getAbsolutePath());

                    // Once converted the files read in our beans, we can compute metrics in a super-easy way.
                    // NB: The class 'CKMetrics' contains a number of other metrics!
                    int LOC = CKMetrics.getLOC(classBean);
                    int LCOM = CKMetrics.getLCOM2(classBean);
                    int CBO = CKMetrics.getCBO(classBean);

                    int ELOC = CKMetrics.getELOC(classBean);
                    int COH = CKMetrics.getCoh(classBean);
                    int WMC = CKMetrics.getWMC(classBean);

                    // Now, let's pretty-print our results.
                    writer.write(
                            LOC,
                            LCOM,
                            CBO,
                            ELOC,
                            COH,
                            WMC,
                            commit.getMsg(),
                            commit.getDate().getTime(),
                            commit.getModifications().get(0).getFileName(),
                            commit.getModifications().get(0).getType()
                    );
                } catch (IOException | IndexOutOfBoundsException | ArithmeticException e) {
                    System.out.println();
                }
            }
        }
    }
    
}
