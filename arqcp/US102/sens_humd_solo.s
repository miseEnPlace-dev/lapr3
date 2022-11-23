.section .text
  .global sens_humd_solo # unsigned char sens_humd_solo(unsigned char ult_hmd_solo, unsigned char ult_pluvio, char comp_rand)
# rdi ult_hmd_solo
# rsi ult_pluvio
# rdx comp_rand

# Limit the max variation using the remainder of division
#
# The random component is divided by the max variation + 1, this way, the ramainder will allways be
# a random value in the interval [-n,n], n = max variation.
sens_humd_solo:
  movb %dil, %al # al = random component
  movb SOIL_HUMD_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation
  incb %cl # cl = max variation + 1

  cbtw # cast byte to word
  divb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value

  ret
