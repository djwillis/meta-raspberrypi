# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel = "linux-raspberrypi"
PREFERRED_PROVIDER_u-boot = "u-boot-rpi"

PREFERRED_PROVIDER_virtual/egl ?= "vc-graphics-hardfp"
PREFERRED_PROVIDER_virtual/libgles1 ?= "vc-graphics-hardfp"
PREFERRED_PROVIDER_virtual/libgles2 ?= "vc-graphics-hardfp"

PREFERRED_PROVIDER_virtual/libgl ?= "mesa-gl"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa-gl"

PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"

PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
PREFERRED_PROVIDER_xserver-nodm-init = "lxdm"


