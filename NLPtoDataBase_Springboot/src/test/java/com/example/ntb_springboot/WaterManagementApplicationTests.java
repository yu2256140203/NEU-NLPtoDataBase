//package com.example.ntb_springboot;
//
//import com.example.ntb_springboot.service.LogService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class WaterManagementApplicationTests {
////    @Autowired
////    private DataSource dataSource;
////
////    @Test
////    void checkMaxLifetime() {
////        if (dataSource instanceof HikariDataSource) {
////            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
////            long maxLifetime = hikariDataSource.getMaxLifetime();
////            System.out.println("maxLifetime value is: " + maxLifetime + " milliseconds");
////        } else {
////            System.out.println("Not using HikariCP as the connection pool.");
////        }
////    }
//
//
//
//    @Autowired
//    LogService logService;
//
//    @Test
//    void contextLoads() {
////        String id = "SJLCQ030002";
////        List<Integer> TwoTds = logService.GetLatestTdsByMachineId(id);
////        int puritytds = TwoTds.get(0);
////        int rawtds = TwoTds.get(1);
////        System.out.println("puritytds: "+puritytds);
////        System.out.println("rawtds: "+rawtds);
//
//        System.out.println("CountLog: "+logService.CountLog());
//    }
//
////    @Autowired
////    MachineService machineService;
////
////    @Test
////    void contextLoads2() {
////        String manufacturer = "";
////        String area = "";
////        String community = "清苑区";
////        String installTimeStart = "2022/11/25";
////        String installTimeEnd = "";
////        String renewTimeStart = "2022/11/26";
////        String renewTimeEnd = "2023/3/10";
////        int from = 0;
////        int num = 20;
////
////        List<Machine> machines = machineService.GetMachinesByFilterAndPage(
////                manufacturer, area, community, installTimeStart, installTimeEnd, renewTimeStart, renewTimeEnd, from, num
////        );
////
////        System.out.println("#####");
////        for (Machine machine : machines) {
////            System.out.println("Machine ID: " + machine.id);
////            System.out.println("Manufacturer: " + machine.manufacturer);
////            System.out.println("Area: " + machine.area);
////            System.out.println("Community: " + machine.community);
////            System.out.println("Install Time: " + machine.installTime);
////            System.out.println("Renew Time: " + machine.renewTime);
////            System.out.println("-----------------------------");
////        }
////
////        int return_num = machineService.CountMachinesByFilter(
////                manufacturer, area, community, installTimeStart, installTimeEnd, renewTimeStart, renewTimeEnd
////        );
////        System.out.println("查询结果总数: " + return_num);
////    }
//
//
//
//}
