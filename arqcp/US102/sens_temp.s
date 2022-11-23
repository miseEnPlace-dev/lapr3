.section .text
  .global sens_temp # char sens_temp(char ult_temp, char comp_rand)
# rdi ult_temp
# rsi comp_rand

# Limit the max variation using the remainder of division
#
# The random component is divided by the max variation + 1, this way, the ramainder will allways be
# a random value in the interval [-n,n], n = max variation.
sens_temp:
  movb %sil, %al # al = random component
  movb TEMP_SENSOR_MAX_VARIATION(%rip), %cl # cl = max variation
  incb %cl # cl = max variation + 1

  cbtw # cast byte to word

  idivb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value

  ret
