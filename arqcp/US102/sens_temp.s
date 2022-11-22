.section .data
  .global TEMP_SENSOR_ATTENUATOR
.section .text
  .global sens_temp # char sens_temp(char ult_temp, char comp_rand)
# rdi ult_temp
# rsi comp_rand

sens_temp:
  movb TEMP_SENSOR_ATTENUATOR(%rip), %al
  cbtw
  imulb %sil

  addb %sil, %al
  ret
