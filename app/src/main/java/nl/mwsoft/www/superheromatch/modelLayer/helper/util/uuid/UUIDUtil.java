package nl.mwsoft.www.superheromatch.modelLayer.helper.util.uuid;

import java.util.UUID;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;

public class UUIDUtil {

    public UUIDUtil() {
    }

    public String generateUUID() {
        return UUID.randomUUID().toString().replace(ConstantRegistry.DASH,ConstantRegistry.EMPTY_STRING);
    }
}
