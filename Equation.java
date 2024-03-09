package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class Equation extends BinaryChromosome {
    private static int bitsEach = 17; //bit allocation
    private static double maxRange = 20.0; //-20 to 20
    private List<Point> points = new ArrayList<>();

    public Equation() {
        super(51); //17 x 3
        randomize();
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    
   //randomization of the chromosome's bits
    @Override
    public void randomize() {
        super.randomize();
    }

    @Override
    public void mutate(double mutationRate) {
        super.mutate(mutationRate);
    }

    @Override
    public Chromosome crossover(Chromosome other) {
        BinaryChromosome offspring = (BinaryChromosome) super.crossover(other);
        Equation eqOffspring = new Equation();
        eqOffspring.bits = offspring.bits; //copy bits from offspring
        eqOffspring.setPoints(this.points); //copy the points for fitness evaluation to the offspring
        return eqOffspring;
    }

    @Override
    public double getFitness() {
        double A = decoder(0);
        double B = decoder(bitsEach);
        double C = decoder(2 * bitsEach);

        double totalDistance = 0;
        for (Point p : points) {
        	//Distance = (Ax + By + C) / square root (A^2 + B^2)
        	//0 being worst fit 1 being best
            double num = Math.abs(A * p.x + B * p.y + C);
            double denom = Math.sqrt(A * A + B * B);
            double distance = num / denom;
            totalDistance += distance;
        }
        return 1 / (1 + (totalDistance / points.size())); // average distance caluclator
    }

    //decodes a coefficient from its prior binary representation
    private double decoder(int startBit) {
        int value = getNumber(startBit, startBit + bitsEach - 1);
        double range = 2 * maxRange;
        return ((value / (double) (1 << bitsEach)) * range) - maxRange;
    }
}
