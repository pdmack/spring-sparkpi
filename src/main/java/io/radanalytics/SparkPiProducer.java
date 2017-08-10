package io.radanalytics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import javax.validation.constraints.*;
import javax.annotation.*;

@Component
public class SparkPiProducer implements Serializable {

    @Value("${sparkpi.jarfile}")
    private String jarFile;

    @PostConstruct
    public void print() {
        System.out.println("submit jar is: "+jarFile);
    }

    public String GetPi() {
        SparkConf sparkConf = new SparkConf().setAppName("JavaSparkPi");
        sparkConf.setJars(new String[]{this.jarFile});
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        int slices = 2;
        int n = 100000 * slices;
        List<Integer> l = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            l.add(i);
        }

        JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

        int count = dataSet.map(integer -> {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            return (x * x + y * y < 1) ? 1 : 0;
        }).reduce((integer, integer2) -> integer + integer2);

        String ret = "Pi is rouuuughly " + 4.0 * count / n;

        jsc.stop();

        return ret;
    }
}
