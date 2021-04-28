package sw_dep_proj;

import org.repodriller.RepoDriller;
import org.repodriller.RepositoryMining;
import org.repodriller.Study;
import org.repodriller.filter.commit.OnlyInBranches;
import org.repodriller.filter.commit.OnlyInMainBranch;
import org.repodriller.filter.commit.OnlyModificationsWithFileTypes;
import org.repodriller.filter.commit.OnlyNoMerge;
import org.repodriller.filter.range.Commits;
import org.repodriller.persistence.csv.CSVFile;
import org.repodriller.scm.CollectConfiguration;
import org.repodriller.scm.GitRemoteRepository;
import org.repodriller.scm.GitRepository;

import java.util.Arrays;
import java.util.Calendar;


// ref : https://github.com/mauricioaniche/repodriller/blob/master/manual/repodriller-2.0.0.md
public class MyStudy implements Study{

    private boolean seleniumMined = false;

    // RepoDriller needs a Study. The interface is quite simple: a single execute() method:
    public static void main(String[] args) {

        new RepoDriller().start(new MyStudy());
    }

/*    // define wich data to extract
    public void b(){
        CollectConfiguration cf = new CollectConfiguration();
        cf.basicOnly();
    }*/


    // configure here your study,
    // projects to analyze, metrics to be executed, and output files
    @Override
    public void execute() {
        // TODO Auto-generated method stub
        //printDevNameForEachCommit();

        if(seleniumMined){
            testOnSelenium();
        }
        else {
            testOnSelenium(); // -- serve fare un controllo nel caso in cui la path esista - usa un boolean
            retrievingSeleniumCommits();
        }
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
/*    public void printDevNameForEachCommit(){
        new RepositoryMining()
                .in(GitRepository.singleProject("C:/Users/abacc.LAPTOP-I2DLL2FP/Desktop/SW_DEP/project and java example/SDAF_on_FOSS/")) // let's test this project
                .through(Commits.all())
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/devsCommits.csv")) // output folder
                .mine();
    }*/

    public void testOnSelenium(){
        GitRemoteRepository.hostedOn("https://github.com/SeleniumHQ/selenium.git")
                .inTempDir("src/main/java/sw_dep_proj/tmpDirStudy/Selenium")
                .asBareRepos()
                .buildAsSCMRepository();
    }

    public void retrievingSeleniumCommits(){

        Calendar fromCal = Calendar.getInstance();
        fromCal.set(2020, 1,1);

        Calendar toCal = Calendar.getInstance();
        toCal.set(2021, 1, 1);

        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\tmpDirStudy\\Selenium")) // let's test this project
                .through(Commits.betweenDates(fromCal, toCal))
                .filters(
                        new OnlyModificationsWithFileTypes(Arrays.asList(".java", ".xml")),
                        new OnlyInBranches(Arrays.asList("master")),
                        new OnlyNoMerge(),
                        new OnlyInMainBranch()
                )
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/seleniumDevsCommits.csv")) // output folder
                .mine();
    }

}
