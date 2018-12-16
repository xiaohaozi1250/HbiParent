package hbi.core.mydemo.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hbi.core.mydemo.dto.Position;
import hbi.core.mydemo.service.IPositionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class IPositionServiceImpl extends BaseServiceImpl<Position> implements IPositionService{

}