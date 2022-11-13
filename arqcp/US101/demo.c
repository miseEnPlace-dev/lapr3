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
uint64_t state = 0;
uint64_t inc = 0;

uint32_t pcg32_random_r()
{
  uint64_t oldstate = state;
  // Advance internal state
  state = oldstate * 6364136223846793005ULL + (inc | 1);
  // Calculate output function (XSH RR), uses old state for max ILP
  uint32_t xorshifted = ((oldstate >> 18u) ^ oldstate) >> 27u;
  uint32_t rot = oldstate >> 59u;
  return (xorshifted >> rot) | (xorshifted << ((-rot) & 31));
}

int main()
{
  int i;
  for (i = 0; i < 32; i++)
    printf("%8x\n", pcg32_random_r());
  return 0;
}
