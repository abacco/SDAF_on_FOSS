package sw_dep_proj;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;


// ref : https://github.com/mauricioaniche/repodriller/blob/master/manual/repodriller-2.0.0.md
public class MyStudy implements Study{

    // RepoDriller needs a Study. The interface is quite simple: a single execute() method:
    public static void main(String[] args) {

        new RepoDriller().start(new MyStudy());
	}
    
    // configure here your study, 
    // projects to analyze, metrics to be executed, and output files

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        printDevNameForEachCommit();
    }
    
    // print the name of the developers for each commit.
    /**
     * --- in(): We use to configure the project (or projects) that will be analyzed.
     * --- through(): The list of commits to analyze. We want all of them.
     * --- filters(): Possible filters to commits, e.g., only commits in a certain branch
     * --- reverseOrder(): Commits will be analysed in reverse order. Default starts from the first commit to the latest one.
     * --- process(): Visitors that will pass in each commit.
     * --- mine(): The magic starts!
     * 
     * --- RepoDriller will open the Git repository and will extract all information that is inside. 
     * --- Then, the framework will pass each commit to all processors.
     * --- N.B. DevelopersVisitor is a processor, go to see its implementasion
     */
    public void printDevNameForEachCommit(){
        new RepositoryMining()
		    .in(GitRepository.singleProject("C:/Users/abacc.LAPTOP-I2DLL2FP/Desktop/SW_DEP/project and java example/SDAF_on_FOSS/")) // let's test this project
		    .through(Commits.all())
		    .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/devsCommits.csv")) // output folder
		    .mine();
    }

    public void a(){
        // GitRemoteRepository
		//     .hostedOn(gitUrl)							// URL like: https://github.com/mauricioaniche/repodriller.git
		//     //.inTempDir(tempDir)							// <Optional>
	    // 	//.asBareRepos()								// <Optional> (1)
		// .buildAsSCMRepository())
    }
}
