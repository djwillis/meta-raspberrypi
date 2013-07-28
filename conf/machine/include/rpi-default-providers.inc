# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel = "linux-raspberrypi"
PREFERRED_PROVIDER_virtual/xserver = "xserver-xorg"
PREFERRED_PROVIDER_virtual/egl ?= "vc-graphics-hardfp"
PREFERRED_PROVIDER_virtual/libgles2 ?= "vc-graphics-hardfp"
PREFERRED_PROVIDER_linux-libc-headers ?= "linux-libc-headers-raspberrypi"
PREFERRED_PROVIDER_linux-firmware ?= "linux-firmware"

# custom ones for graphics, etc
PREFERRED_PROVIDER_jpeg = "libjpeg-turbo"
PREFERRED_PROVIDER_jpeg-native = "libjpeg-turbo-native"
PREFERRED_PROVIDER_libasound-module-bluez ?= "bluez4"
