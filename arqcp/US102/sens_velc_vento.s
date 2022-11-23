.section .text
  .global sens_velc_vento # char sens_velc_vento(unsigned char ult_velc_vento, char comp_rand)

# rdi ult_velc_vento
# rsi comp_rand

sens_velc_vento:

  movb %sil, %al # al = random component
  movb VELC_SENSOR_MAX_VARIATION (%rip), %cl # cl = max variation
  incb %cl # cl = max variation + 1

  cbtw # cast byte to word

  divb %cl # divide random component by max variation (remainder in %ah)

  shrw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value

ret
