package hcloud.platform.modules.plalog.dao;

import hcloud.platform.modules.plalog.model.dto.PlaLogListDto;
import hcloud.platform.modules.plalog.model.entity.PlaLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@Repository
public interface PlaLogDao extends BaseMapper<PlaLog> {

    List<PlaLogListDto> list();
}
