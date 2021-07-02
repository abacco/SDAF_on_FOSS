package sw_dep_proj.ml;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import weka.classifiers.trees.RandomForest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PipelineRunner {

    public static void main(String args[]) throws IOException, InterruptedException {

        mergeCSV("netflix");
        mergeCSV("graal");

        Pipeline pipelineNetflix = new Pipeline("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\final_data\\netflix_real_dataset.csv",  // insert here the path of your dataset in input
                "src/main/java/sw_dep_proj/final_data/netflix_ml_output.csv", // insert here the path to your output csv
                new RandomForest(), "netflix");

        Pipeline pipelineGraal = new Pipeline("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\final_data\\graal_real_dataset.csv",  // insert here the path of your dataset in input
                "src/main/java/sw_dep_proj/final_data/graal_ml_output.csv", // insert here the path to your output csv
                new RandomForest(), "graal");

        Pipeline pipelineGraalAndNetflix = new Pipeline("C:\\Users\\bacco\\OneDrive\\Desktop\\progetti uni\\SDAF_on_FOSS\\src\\main\\java\\sw_dep_proj\\final_data\\graalAndNetflix_real_dataset.csv",  // insert here the path of your dataset in input
                "src/main/java/sw_dep_proj/final_data/graalAndNetflix_ml_output.csv", // insert here the path to your output csv
                new RandomForest(), "graalAndNetflix");
    }

    public static void mergeCSV(String projectName) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader("src/main/java/sw_dep_proj/csv/" + projectName + "Dataset.csv"));
        BufferedReader csvReader2 = new BufferedReader(new FileReader("src/main/java/sw_dep_proj/final_data/"+ projectName + "_smells.csv"));

        CSVWriter c = new CSVWriter(new FileWriter("src/main/java/sw_dep_proj/final_data/" + projectName + "_real_dataset.csv"), ',', CSVWriter.NO_QUOTE_CHARACTER);

        String row, row2;
        while ((row = csvReader.readLine()) != null && (row2 = csvReader2.readLine()) != null) {
            String[] data = row.split(",");
            String[] data1 = row2.split(",");

            c.writeNext(ArrayUtils.addAll(data, data1));
        }

        csvReader.close();
        csvReader2.close();
        c.close();
    }
}
