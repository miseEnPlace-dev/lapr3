.section .text
  .global sens_humd_atm # char sens_humd_atm(unsigned char ult_hmd_atm, unsigned char ult_pluvio, char comp_rand)

# rdi ult_hmd_atm
# rsi ult_pluvio
# rdx comp_rand

sens_humd_atm:
ret
