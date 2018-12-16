package hbi.core.mydemo.service;

import com.hand.hap.core.ProxySelf;
import com.hand.hap.system.service.IBaseService;
import hbi.core.mydemo.dto.Position;

public interface IPositionService extends IBaseService<Position>, ProxySelf<IPositionService> {

}