package service;

import dao.Repository;
import model.Candidate;
import model.Vote;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void generatePDF(List<Vote> votesList, String filePath) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.beginText();
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
            contentStream.newLineAtOffset(100, 700);

            // Создаем Map для подсчета голосов за каждого кандидата
            Map<Integer, Integer> votesCountMap = new HashMap<>();

            for (Vote vote : votesList) {
                // Переносим каждый голос на новую строку
                contentStream.showText(vote.toString());
                contentStream.newLine();

                // Увеличиваем счетчик голосов за каждого кандидата
                int candidateId = vote.getCandidateId();
                votesCountMap.put(candidateId, votesCountMap.getOrDefault(candidateId, 0) + 1);
            }

            // Выводим информацию о количестве голосов за каждого кандидата в конце PDF
            contentStream.showText("\n\n:\n".replace("\n", "").replace("\r", ""));
            for (Map.Entry<Integer, Integer> entry : votesCountMap.entrySet()) {
                contentStream.showText(" ID " + entry.getKey() + ": " + entry.getValue() + " \n".replace("\n", "").replace("\r", ""));
            }

            contentStream.endText();
            contentStream.close();

            document.save(filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}