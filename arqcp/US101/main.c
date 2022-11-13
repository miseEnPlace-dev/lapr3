/*
 * =====================================================================================
 *  PCG Random generator
 *
 *  See:  https://www.pcg-random.org
 *
 *  "Uglyfied" for an easier  assembly translation
 *
 *  Note: the values of state and inc should be initialized with values from /dev/random
 *
 * =====================================================================================
 */

#include <stdio.h>
#include <stdint.h>
#include "random.h"
#include "dev_random.h"		

uint64_t state = 0;
uint64_t inc = 0;

int main() {
	int i;

	state = get_value_from_dev_random();
	inc = get_value_from_dev_random();

	printf("Value of state: %lu\n", state);
	printf("Value of inc: %lu\n", inc);

	printf("Values from pcg32_random_r():\n");

  	for (i = 0; i < 32; i++)
    		printf("%8x\n", pcg32_random_r());

  	return 0;
}

