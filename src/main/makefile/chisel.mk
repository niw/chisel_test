$(OUTPUT_DIR)/$(TOP_MODULE).v:
	sbt 'runMain chisel3.stage.ChiselMain --target-dir=$(OUTPUT_DIR) --module=$(TOP_MODULE)'
