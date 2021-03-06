package pl.akjos.training_manager.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.akjos.training_manager.domain.model.UserTraining;
import pl.akjos.training_manager.userTraining.UserTrainingDetailsDTO;
import pl.akjos.training_manager.userTraining.UserTrainingDetailsToManageDTO;
import pl.akjos.training_manager.userTraining.UserTrainingListDTO;
import pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO;

import java.util.List;

public interface UserTrainingRepository extends JpaRepository<UserTraining, Long> {
    @Query("SELECT COUNT(ut) FROM UserTraining ut WHERE ut.trainingId = :id AND ut.denied = FALSE")
    Integer countAllById(Long id);

    @Query("SELECT COUNT(ut) FROM UserTraining ut JOIN Training t ON ut.trainingId = t.id WHERE t.title = :title AND ut.acceptByManager = TRUE")
    Integer countAllByTrainingTitleAndAcceptByManager(String title);

    @Query("SELECT ut.user.department.id FROM UserTraining ut WHERE ut.id = :id")
    Long getDepartmentIdById(Long id);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingListDTO(ut.id, t.title, ut.acceptByTeamLeader, ut.acceptByManager, ut.denied) FROM UserTraining ut LEFT JOIN Training t ON t.id = ut.trainingId WHERE ut.userId =:userId")
    List<UserTrainingListDTO> getAllUserTrainingListDTOByUserId(Long userId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingDetailsDTO(ut.id, t.title, t.description, t.trainingDays, t.dataStart, ut.acceptByTeamLeader, ut.acceptByManager, ut.denied) FROM UserTraining ut JOIN Training t ON ut.trainingId = t.id WHERE ut.id = :userTrainingId AND ut.userId = :userId")
    UserTrainingDetailsDTO getByUserIdAndTrainingId(Long userId, Long userTrainingId);

    UserTraining getToDeleteByIdAndUserId(Long id, Long userId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingDetailsToManageDTO(ut.id, t.title, t.description, t.trainingDays, t.quantityAvailable, t.price, t.dataStart) FROM UserTraining ut JOIN ut.training t WHERE ut.id = :id")
    UserTrainingDetailsToManageDTO getUserTrainingDetailsToManageById(Long id);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = FALSE AND ut.denied = FALSE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingByDepartmentIdForTeamLeader(Long departmentId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = FALSE AND ut.denied = TRUE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingDeniedToEditForTeamLeaderByDepartmentId(Long departmentId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = TRUE AND ut.denied = FALSE AND ut.acceptByManager = FALSE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingAcceptToEditForTeamLeaderByDepartmentId(Long departmentId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = TRUE AND ut.acceptByManager = FALSE AND ut.denied = FALSE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingByDepartmentIdForManager(Long departmentId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = TRUE AND ut.denied = FALSE AND ut.acceptByManager = TRUE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingAcceptToEditForManagerByDepartmentId(Long departmentId);

    @Query(value = "SELECT new pl.akjos.training_manager.userTraining.UserTrainingViewToManageListDTO(ut.id, u.userDetails.firstName, u.userDetails.lastName, t.title, t.price, t.trainingDays, t.dataStart) FROM UserTraining ut JOIN ut.training t JOIN ut.user u JOIN u.department d WHERE u.active = TRUE AND d.id = :departmentId AND t.active = TRUE AND ut.acceptByTeamLeader = TRUE AND ut.denied = TRUE AND ut.acceptByManager = FALSE")
    List<UserTrainingViewToManageListDTO> getAllUserTrainingDeniedToEditForManagerByDepartmentId(Long departmentId);
}
