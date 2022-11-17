#include <stdio.h>

int dailySensorsSummary(){
	const int LINES = 6;
	const int COLUMNS = 3;
	double result[LINES][COLUMNS];
	/*
	Lines:
		- temperature (ºC)
		- wind velocity (km/h)
		- wind direction (º)
		- atmospheric humidity (%)
		- soil humidity (%)
		- rainfall (mm)
	Columns:
		- maximum value
		- minimum value
		- average value
	*/

	double sumAux[LINES];
	int countAux[COLUMNS];

	// Initialize arrays with -1
	for(int i = 0; i<LINES; i++){
		*(sumAux + i) = -1;
		*(countAux + i) = -1;
	}


	/*
		Set maxs and mins
		Set sums and counts
	*/
	/*
		TODO
		(waiting for us102)
	*/


	//Set the average value
	for(int i = 0; i<LINES; i++){
		int count = *(sumAux + i); // number of sensors of specific type
		if(count != 0){
			double sum = *(sumAux + i); // sum of values read from sensors of specific type
			*((result + i)*COLUMNS + COLUMNS - 1) = sum/(double)count; // set the average in the main array
		}
	}

}
