package com.comitfy.rabbitmqflink.service;

import com.comitfy.rabbitmqflink.dto.EKGMeasurementDTO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EncodingService {


    static List<Integer> encodeEcgVals(int d1, int d2) {
        final int fd1 = (d1 + (2048)) & 4095;
        final int fd2 = (d2 + (2048)) & 4095;
        final List<Integer> set1 = new ArrayList<>();
        set1.add(fd1 & 0xFF);
        set1.add(((fd1 >> 8) & 0x000F) | ((fd2 & 0xFF) << 4));
        set1.add((fd2 >> 4) & 0xFF);
        return set1;
    }

    public static byte[] encode(List<EKGMeasurementDTO> ekgMeasurementDTOList) {

        List<Integer> values = new ArrayList<>();
        List<Integer> leads = new ArrayList<>();
        List<Integer> buffer = new ArrayList<>();

        int size = 8;
        List<Byte> valByteA = new ArrayList<>();


        for (int i = 0; i < ekgMeasurementDTOList.size(); i++) {

            buffer.add( Double.valueOf(ekgMeasurementDTOList.get(i).getVal()).intValue());

            if (ekgMeasurementDTOList.get(i).getIsLead() != null && ekgMeasurementDTOList.get(i).getIsLead()) {
                leads.add(1);
            } else {
                leads.add(0);
            }


            if ((i + 1) % 8 == 0 && i != 0) {

                for (int j = 0; j < buffer.size(); j++) {

                    if (j != 0 && j % 2 != 0) {
                        List<Integer> encodeEcgValsArr = encodeEcgVals(buffer.get(j - 1), buffer.get(j));
                        values.addAll(encodeEcgValsArr);
                        for (Integer encodeEcgVals : encodeEcgValsArr) {
                            valByteA.add(encodeEcgVals.byteValue());
                        }
                    }

                }
                List<Integer> leadsReverse = new ArrayList<>();


                Collections.reverse(leads);

                String leadsReverseAppended = "";

                for (Integer lead : leads) {
                    leadsReverseAppended += String.valueOf(lead);
                }

                valByteA.add(Integer.valueOf(Integer.parseInt(leadsReverseAppended, 2)).byteValue());
                buffer.clear();
                leads.clear();
            }
        }


        //byte[] bytes = ArrayUtils.toPrimitive(valByteA.toArray(new Byte[0]));
       // System.out.println(Base64.getEncoder().encode(bytes).toString());
       // Base64.getEncoder().encode(bytes);
        //String result = new String(Base64.getEncoder().encode(bytes));
        //System.out.println(result);
        return ArrayUtils.toPrimitive(valByteA.toArray(new Byte[0]));

    }


}
