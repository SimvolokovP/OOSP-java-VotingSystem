package service;

import dao.Repository;
import model.Candidate;
import model.Vote;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CIKService {
    private final Repository repository;

    public CIKService(Repository repository) {
        this.repository = repository;
    }

    public void createNewCandidate(Candidate candidate) {
        repository.createNewCandidate(candidate);
    }

    public List<Vote> getVotesList() {
        return repository.getVotesList();
    }

    public  void generatePDF(String filePath, List<Vote> votesList) {
        try (PDDocument document = new PDDocument()) {
            if (Objects.equals(filePath, "") || filePath.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                filePath = "votes_report_" + sdf.format(new Date()) + ".pdf";
            }
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);


            Map<Integer, Integer> votesCountMap = new HashMap<>();

            float yPosition = 700;

            for (Vote vote : votesList) {
                String text = vote.toString();


                if (yPosition - 14 < 50) {
                    contentStream.endText();
                    contentStream.close();


                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, 700);
                    yPosition = 700;
                }


                contentStream.showText(text);
                contentStream.newLineAtOffset(0, -14);
                yPosition -= 14;


                int candidateId = vote.getCandidateId();
                votesCountMap.put(candidateId, votesCountMap.getOrDefault(candidateId, 0) + 1);
            }


            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.newLineAtOffset(100, yPosition - 20);
            for (Map.Entry<Integer, Integer> entry : votesCountMap.entrySet()) {
                contentStream.showText("Candidate with ID " + entry.getKey() + ": " + entry.getValue() + " votes");
                contentStream.newLineAtOffset(0, -14);
                yPosition -= 14;
            }

            contentStream.endText();
            contentStream.close();

            document.save(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }
