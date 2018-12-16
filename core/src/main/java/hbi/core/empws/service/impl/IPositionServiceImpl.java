package hbi.core.empws.service.impl;

import com.hand.hap.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import hbi.core.empws.dto.Position;
import hbi.core.empws.service.IPositionService;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class IPositionServiceImpl extends BaseServiceImpl<Position> implements IPositionService{

}