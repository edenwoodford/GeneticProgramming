package geneticAlgorithms;

import java.util.ArrayList;
import java.util.Random;

public class Population {
    private ArrayList<Chromosome> chromosomes;
    double mutationRate;
    public Population(double mutationRate) {
        this.chromosomes = new ArrayList<>();
        this.mutationRate = mutationRate;
    }
    //add chromosome
    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }
    public Chromosome evaluate() {
        Chromosome bestFit = null;
        double bestFitness = 0;
        //iterate through to find the best fit
        for (Chromosome c : chromosomes) {
        double fitness = c.getFitness();
        if (fitness > bestFitness) {
            bestFitness = fitness;
            bestFit = c;
            }
        }
        return bestFit;
    }

    public void breed() {
        ArrayList<Chromosome> newGeneration = new ArrayList<>();
        ArrayList<Double> tickets = new ArrayList<>();
        double totalFitness = 0;
        // this will be used to select parents for breeding proportionally to fitness
        for (Chromosome c : chromosomes) {
        if (c.getFitness() > 0) {
            totalFitness += c.getFitness();
            tickets.add(totalFitness);
            }
        }

        Random rand = new Random();
        //breeding chromosomes to create the new generation
        while (newGeneration.size() < chromosomes.size()) {
            //seleting two parents
            Chromosome parent1 = selectParent(rand.nextDouble() * totalFitness, tickets);
            Chromosome parent2 = selectParent(rand.nextDouble() * totalFitness, tickets);
            Chromosome child = parent1.crossover(parent2);
            child.mutate(mutationRate);
            //check for postive fitness
            if (child.getFitness() > 0) {
                newGeneration.add(child);
            }
        }

        // update the generation
        this.chromosomes = newGeneration;
    }
    private Chromosome selectParent(double randFitness, ArrayList<Double> tickets) {
        for (int i = 0; i < tickets.size(); i++) {
            // finds chromosome whose cumulative fitness score exceeds the random fitness value.
            if (randFitness <= tickets.get(i)) {
                return chromosomes.get(i);
            }
        }
        //if no chromosome is found
    return chromosomes.get(chromosomes.size()-1);
    }
    // update mutation rate
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
    // Get the list of chromosomes in the population
    public ArrayList<Chromosome> getChromosomes() {
        return chromosomes;
    }
}
