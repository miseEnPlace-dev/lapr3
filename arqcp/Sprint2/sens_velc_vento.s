.section .text
  .global sens_velc_vento # unsigned char sens_velc_vento(unsigned char ult_velc_vento, char comp_rand)

# rdi ult_velc_vento
# rsi comp_rand

sens_velc_vento:
  movb %sil, %al # al = random component
  cbtw # cast byte to word

  testw %ax, %ax
  je compRandZero

  movb VELC_SENSOR_MAX_VARIATION (%rip), %cl # cl = max variation
  incb %cl # cl = max variation + 1

  idivb %cl # divide random component by max variation (remainder in %ah)

  sarw $8, %ax # get the value to right position (%al)

  addb %dil, %al # add to last random value

  jmp continue

compRandZero:

  movb %dil, %al # al = last random value
  jmp end

compRandNeg:
  negw %ax # make positive
  jmp end

continue:
  testb %al, %al
  js compRandNeg

end:
ret
