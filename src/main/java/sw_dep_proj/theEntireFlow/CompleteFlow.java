package sw_dep_proj.theEntireFlow;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.ArrayUtils;
import sw_dep_proj.MyStudy;
import sw_dep_proj.code_smells.CodeSmellDetector;

import java.io.*;
import java.sql.SQLException;

import sw_dep_proj.ml.PipelineRunner;

public class CompleteFlow {

    public static void main(String args[]) throws IOException, SQLException, InterruptedException {

        MyStudy.main(null);
        CodeSmellDetector.main(null);

/*        mergeCsv("azure");
        mergeCsv("graal");
        mergeCsv("netflix");
        mergeCsv("shopizer");*/

        PipelineRunner.main(null);

    }

    public static void mergeCsv(String projectName) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader("src/main/java/final_data/" + projectName + "Dataset.csv"));
        BufferedReader csvReader2 = new BufferedReader(new FileReader("src/main/java/final_data/" + projectName + "output_smell_detector2.csv"));

        CSVWriter c = new CSVWriter(new FileWriter("src/main/java/final_data/" + projectName.concat("real_dataset.csv")), ',', CSVWriter.NO_QUOTE_CHARACTER);

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
