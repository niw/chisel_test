PCF_SRC_DIR=src/main/pcf/tinyfpgabx

.PHONY: all
all: $(OUTPUT_DIR)/$(TOP_MODULE).bin

.PHONY: prog
prog: $(OUTPUT_DIR)/$(TOP_MODULE).bin
	tinyprog -p $<

%.json: %.v
	yosys -p "synth_ice40 -json $@" $<

# Can't use pattern rule for this target.
$(OUTPUT_DIR)/$(TOP_MODULE).asc: $(OUTPUT_DIR)/$(TOP_MODULE).json $(PCF_SRC_DIR)/$(TOP_MODULE).pcf
	nextpnr-ice40 \
	--lp8k \
	--package cm81 \
	--json $(OUTPUT_DIR)/$(TOP_MODULE).json \
	--pcf $(PCF_SRC_DIR)/$(TOP_MODULE).pcf \
	--asc $@ \
	$(NEXTPNR_ARGS)

%.bin: %.asc
	icepack $< $@
