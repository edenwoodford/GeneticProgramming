package geneticAlgorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneticMain {
    public static void main(String[] args) {
        int populationSize = 1000;
        int generations = 100000;
        double fitnessGoal = .99;
        double mutationRate = 0.1;
        //"likelihood of mutatations each generation" verbatim
        List<Point> points = new ArrayList<>();

        //loading & parsing points from the test.txt file
        try (BufferedReader br = new BufferedReader(new FileReader("src/geneticAlgorithms/test.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    points.add(new Point(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Population pop = new Population(mutationRate);

        //initialize the population with random chromosomes
        for (int i = 0; i < populationSize; i++) {
            Equation eq = new Equation();
            eq.setPoints(points); //sets the points for each Equation instance
            pop.addChromosome(eq);
        }

        double currentFit = 0;
        int genCount = 0;
        for (int i = 0; i < generations; i++) {
            Chromosome best = pop.evaluate();
            double bestFitness = best.getFitness();
            if (bestFitness > currentFit) {
                currentFit = bestFitness;
                System.out.println("New best fitness: " + currentFit);
            }

            if (currentFit >= fitnessGoal) {
                System.out.println("Success: Fitness goal of 0.99 has been met!");
                System.out.println("Generations it took: " + (genCount + 1));
                System.out.println("Best fitness: " + currentFit);
                break;
            }

            pop.breed();
            genCount++;
        }
       if (currentFit < fitnessGoal) {
            System.out.println("Fitness goal of 0.99 was not met w/ max generations reached.");
        }
    }
}
