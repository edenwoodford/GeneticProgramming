package geneticAlgorithms;

public interface Chromosome {
    Chromosome crossover(Chromosome other);
    void mutate(double mutationRate);
    double getFitness();
}