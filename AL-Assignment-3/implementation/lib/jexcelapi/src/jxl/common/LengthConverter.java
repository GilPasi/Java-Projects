/*********************************************************************
*
*      Copyright (C) 2006 Andrew Khan
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNu Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOuT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICuLAR PuRPOSE.  See the GNu
* Lesser General Public License for more details.
*
* You should have received a copy of the GNu Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 uSA
***************************************************************************/

package jxl.common;

public class LengthConverter
{
  private static double[][] factors = 
    new double[Lengthunit.getCount()][Lengthunit.getCount()];

  static
  {
    // The identity factors
    factors[Lengthunit.POINTS.getIndex()][Lengthunit.POINTS.getIndex()] = 1;
    factors[Lengthunit.METRES.getIndex()][Lengthunit.METRES.getIndex()] = 1;
    factors[Lengthunit.CENTIMETRES.getIndex()][Lengthunit.CENTIMETRES.getIndex()] = 1;
    factors[Lengthunit.INCHES.getIndex()][Lengthunit.INCHES.getIndex()] = 1;

    // The points conversion factors
    factors[Lengthunit.POINTS.getIndex()][Lengthunit.METRES.getIndex()] = 0.00035277777778;
    factors[Lengthunit.POINTS.getIndex()][Lengthunit.CENTIMETRES.getIndex()] = 0.035277777778;
    factors[Lengthunit.POINTS.getIndex()][Lengthunit.INCHES.getIndex()] = 0.013888888889;

    // The metres conversion factors
    factors[Lengthunit.METRES.getIndex()][Lengthunit.POINTS.getIndex()] = 2877.84;
    factors[Lengthunit.METRES.getIndex()][Lengthunit.CENTIMETRES.getIndex()] = 100;
    factors[Lengthunit.METRES.getIndex()][Lengthunit.INCHES.getIndex()] = 39.37;

    // The centimetres conversion factors
    factors[Lengthunit.CENTIMETRES.getIndex()][Lengthunit.POINTS.getIndex()] = 28.34643;
    factors[Lengthunit.CENTIMETRES.getIndex()][Lengthunit.METRES.getIndex()] = 0.01;
    factors[Lengthunit.CENTIMETRES.getIndex()][Lengthunit.INCHES.getIndex()] = 0.3937;

    // The inches conversion factors
    factors[Lengthunit.INCHES.getIndex()][Lengthunit.POINTS.getIndex()] = 72;
    factors[Lengthunit.INCHES.getIndex()][Lengthunit.METRES.getIndex()] = 0.0254;
    factors[Lengthunit.INCHES.getIndex()][Lengthunit.CENTIMETRES.getIndex()] = 2.54;
  }

  public static double getConversionFactor(Lengthunit from, Lengthunit to)
  {
    return factors[from.getIndex()][to.getIndex()];
  }
}
