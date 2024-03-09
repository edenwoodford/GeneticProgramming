package geneticAlgorithms;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class GeneticTesting {

    @Test
    void testStartUp() {
        Equation chromosome = new Equation();
        assertNotNull(chromosome.bits, "bits should not be null.");
        assertEquals(51, chromosome.bits.length, "Chromosome should be initialized with 51 bits");
    }

    @Test
    void testFitness() {
        Equation chromosome = new Equation();
        chromosome.bits[0] = 1;
        chromosome.bits[1] = 1;
        chromosome.bits[2] = 1;

        List<Point> points = new ArrayList<>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        chromosome.setPoints(points);

        double actualFitness = chromosome.getFitness();
        double expectedFitness = 0.2;//manually calculated (rounded)
        
        assertEquals(expectedFitness, actualFitness, 0.5, "Expected and Actual");
    }

    @Test
    void testMutation() {
        Equation chromosome = new Equation();
        int[] bitsBeforeMutation = chromosome.bits.clone();
        chromosome.mutate(1.0);
        boolean isMutated = false;
        for (int i = 0; i < chromosome.bits.length; i++) {
            if (bitsBeforeMutation[i] != chromosome.bits[i]) {
                isMutated = true;
                break;
            }
        }
        assertTrue(isMutated, "Chromosome should 100% mutate.Any bit change will prove that.");
    }
    @Test
    void testEvaluate() {
        Population population = new Population(0.1);//mutation rate should not matter for this test
        Equation chromosome1 = new Equation();
        Equation chromosome2 = new Equation();
        chromosome1.bits[0] = 1;
        chromosome2.bits[0] = 0;
        population.addChromosome(chromosome1);
        population.addChromosome(chromosome2);

        Chromosome bestChromosome = population.evaluate();

        assertNotNull(bestChromosome, "Best chromosome should not be null.");
        assertEquals(chromosome1, bestChromosome, "the higher fitness should be selected as the best.");
    }

    @Test
    void testBreed() {
        Population population = new Population(0.1);
        Equation chromosome1 = new Equation();
        Equation chromosome2 = new Equation();
        chromosome1.bits[0] = 1;
        chromosome2.bits[0] = 0;
        population.addChromosome(chromosome1);
        population.addChromosome(chromosome2);

        int initialPopulationSize = population.getChromosomes().size();
        population.breed();
        assertEquals(initialPopulationSize, population.getChromosomes().size(), "Population size should remain the same");
    }

    @Test
    void testSetMutationRate() {
        Population population = new Population(0.1);
        double newMutationRate = 0.2;
        population.setMutationRate(newMutationRate);

        assertEquals(newMutationRate, population.mutationRate, "Mutation rate is correct");
    }//tests passed!
}