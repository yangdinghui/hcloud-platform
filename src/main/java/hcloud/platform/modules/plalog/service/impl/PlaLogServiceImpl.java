package hcloud.platform.modules.plalog.service.impl;

import hcloud.platform.base.utils.DateUtil;
import hcloud.platform.modules.plalog.dao.PlaLogDao;
import hcloud.platform.modules.plalog.model.dto.PlaLogListDto;
import hcloud.platform.modules.plalog.model.entity.PlaLog;
import hcloud.platform.modules.plalog.model.vo.PlaLogSaveVo;
import hcloud.platform.modules.plalog.service.PlaLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@Service
public class PlaLogServiceImpl implements PlaLogService {

    @Autowired
    private PlaLogDao plaLogDao;

    @Override
    public boolean save(PlaLogSaveVo vo) {
        int count = 0;
        PlaLog log = new PlaLog();
        BeanUtils.copyProperties(vo, log);
        log.setLogTime(new Date());
        log.setLogCode(UUID.randomUUID().toString());
        count = plaLogDao.insertSelective(log);
        return count > 0;
    }

    @Override
    public List<PlaLogListDto> list() {
        return plaLogDao.list();
    }

}
