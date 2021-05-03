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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;


// ref : https://github.com/mauricioaniche/repodriller/blob/master/manual/repodriller-2.0.0.md
public class MyStudy implements Study{

    private final String azureRepo = "https://github.com/Azure/azure-sdk-for-java.git";
    private final String oracleRepo = "https://github.com/oracle/graal.git";
    private final String netflixRepo = "https://github.com/Netflix/zuul.git";
    private final String seleniumRepo = "https://github.com/SeleniumHQ/selenium.git";
    private final String shopizerRepo = "https://github.com/shopizer-ecommerce/shopizer.git";

    // RepoDriller needs a Study. The interface is quite simple: a single execute() method:
    public static void main(String[] args) {

        new RepoDriller().start(new MyStudy());
    }


    public void mineRepos(){

        Path azurePath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/azure");
        boolean azureMined = Files.exists(azurePath);

        Path seleniumPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/selenium");
        boolean seleniumMined = Files.exists(seleniumPath);

        Path netflixPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/netflix");
        boolean netflixMined = Files.exists(netflixPath);

        Path oraclePath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/oracle");
        boolean oracleMined = Files.exists(oraclePath);

        Path shopizerPath = Paths.get("src/main/java/sw_dep_proj/tmpDirStudy/shopizer");
        boolean shopizerMined = Files.exists(shopizerPath);

        if(!seleniumMined && !netflixMined && !azureMined && !oracleMined && !shopizerMined ){
            buildRepo(seleniumRepo, "selenium");
            buildRepo(netflixRepo, "netflix");
            buildRepo(azureRepo, "azure");
            buildRepo(oracleRepo, "oracle");
            buildRepo(shopizerRepo, "shopizer");
        }
    }
    // configure here your study,
    // projects to analyze, metrics to be executed, and output files
    @Override
    public void execute() {

        //mineRepos();
        System.out.println("repo mined - retrieving commits");
            retrieveRepoCommits("selenium", "seleniumCSV");
            retrieveRepoCommits("netflix", "netflixCSV");
            retrieveRepoCommits("azure", "azureCSV");
            retrieveRepoCommits("oracle", "oracleCSV");
            retrieveRepoCommits("shopizer", "shopizerCSV");
    }

    public void buildRepo(String githubLink, String dir){
        GitRemoteRepository.hostedOn(githubLink)
                .inTempDir("src/main/java/sw_dep_proj/tmpDirStudy/" + dir)
                .asBareRepos()
                .buildAsSCMRepository();
    }

    public void retrieveRepoCommits(String projectRepoOutput, String projectCsvOutput){
        Calendar fromCal = Calendar.getInstance();
        fromCal.set(2020, 1,1);

        Calendar toCal = Calendar.getInstance();
        toCal.set(2021, 1, 1);

        new RepositoryMining()
                .in(GitRepository.singleProject("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\tmpDirStudy\\" + projectRepoOutput)) // let's test this project
                .through(Commits.betweenDates(fromCal, toCal))
                .filters(
                        new OnlyModificationsWithFileTypes(Arrays.asList(".java")),
                        new OnlyInBranches(Arrays.asList("master")),
                        new OnlyNoMerge(),
                        new OnlyInMainBranch()
                )
                .process(new DevelopersVisitor(), new CSVFile("src/main/java/sw_dep_proj/csv/" + projectCsvOutput)) // output folder
                .mine();
    }
}
