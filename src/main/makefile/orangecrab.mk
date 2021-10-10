LPF_SRC_DIR=src/main/lpf/orangecrab

.PHONY: all
all: $(OUTPUT_DIR)/$(TOP_MODULE).dfu

.PHONY: dfu
dfu: $(OUTPUT_DIR)/$(TOP_MODULE).dfu
	dfu-util $<

%.json: %.v
	yosys -p "synth_ecp5 -json $@" $<

# Can't use pattern rule for this target.
$(OUTPUT_DIR)/$(TOP_MODULE).config: $(OUTPUT_DIR)/$(TOP_MODULE).json $(LPF_SRC_DIR)/$(TOP_MODULE).lpf
	nextpnr-ecp5 \
    --25k \
    --package CSFBGA285 \
    --json $(OUTPUT_DIR)/$(TOP_MODULE).json \
    --lpf $(LPF_SRC_DIR)/$(TOP_MODULE).lpf \
    --textcfg $@

%.bit: %.config
	ecppack --compress --freq 38.8 --input $< --bit $@

%.dfu: %.bit
	cp $< $@
	dfu-suffix -v 1209 -p 5af0 -a $@
