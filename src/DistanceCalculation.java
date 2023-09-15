/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Daniel Moreno
 * @param <N> for the class being compared
 */
public interface DistanceCalculation <N extends Comparable<? super N>> {
    public double compareDistance(N otherPlace);
}
