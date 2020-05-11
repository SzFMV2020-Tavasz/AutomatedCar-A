package hu.oe.nik.szfmv.automatedcar.move;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

/**@author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
interface ICarLocation {

    /**Position of the car as of X and Y coordinates.*/
    IVector getPosition();

    /**The facing direction of the car, so a vector pointing in the direction from the back of the car to its front.*/
    IVector getFacing();

}
