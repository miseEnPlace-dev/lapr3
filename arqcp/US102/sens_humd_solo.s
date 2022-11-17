.section .text
  .global sens_humd_solo # char sens_humd_solo(unsigned char ult_hmd_solo, unsigned char ult_pluvio, char comp_rand)

# rdi ult_hmd_solo
# rsi ult_pluvio
# rdx comp_rand

sens_humd_solo:
ret
