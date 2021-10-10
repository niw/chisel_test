ifndef BOARD
$(error BOARD is not specified.)
endif

ifndef TOP_MODULE
$(error TOP_MODULE is not specified.)
endif

OUTPUT_DIR=out

MAKRFILE_SRC_DIR=src/main/makefile

include $(MAKRFILE_SRC_DIR)/$(BOARD).mk
include $(MAKRFILE_SRC_DIR)/chisel.mk
