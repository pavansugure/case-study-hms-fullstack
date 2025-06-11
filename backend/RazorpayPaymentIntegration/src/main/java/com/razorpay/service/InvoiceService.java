package com.razorpay.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.colors.DeviceGray;
import com.razorpay.model.Orders;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class InvoiceService {

    public String generateInvoice(Orders order) {
        try {
            String directoryPath = "invoices/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = directoryPath + order.getOrderId() + "_invoice_" + timestamp + ".pdf";

            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.setMargins(20, 20, 20, 20);

            // Add logo (optional)
            try {
                String logoPath = "src/main/resources/static/HotelLogo.jpeg";
                ImageData imageData = ImageDataFactory.create(logoPath);
                Image logo = new Image(imageData).scaleToFit(100, 100).setFixedPosition(470, 750);
                document.add(logo);
            } catch (Exception e) {
                System.out.println("Logo not found. Skipping.");
            }

            // Title
            document.add(new Paragraph("Reservation Invoice")
                    .setBold()
                    .setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph("Order ID: " + order.getOrderId()).setFontSize(12));
            document.add(new Paragraph("Generated On: " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).setFontSize(12));
            document.add(new Paragraph("Amount Paid: ₹" + order.getAmount()).setFontSize(12).setBold());

            // Guest Information Table
            document.add(new Paragraph("\nGuest Details")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline()
                    .setMarginTop(15)
                    .setMarginBottom(10));

            float[] guestColumnWidths = {150F, 350F};
            Table guestTable = new Table(guestColumnWidths);

            guestTable.addCell(new Cell().add(new Paragraph("Name").setBold()));
            guestTable.addCell(new Cell().add(new Paragraph(order.getGuestName())));

            guestTable.addCell(new Cell().add(new Paragraph("Email").setBold()));
            guestTable.addCell(new Cell().add(new Paragraph(order.getEmail())));

            guestTable.addCell(new Cell().add(new Paragraph("Phone").setBold()));
            guestTable.addCell(new Cell().add(new Paragraph(order.getPhoneNo())));

            guestTable.addCell(new Cell().add(new Paragraph("Address").setBold()));
            guestTable.addCell(new Cell().add(new Paragraph(order.getAddress())));

            document.add(guestTable);

            // Reservation Details Table
            document.add(new Paragraph("\nReservation Details")
                    .setBold()
                    .setFontSize(14)
                    .setUnderline()
                    .setMarginTop(15)
                    .setMarginBottom(10));

            float[] resColumnWidths = {150F, 350F};
            Table resTable = new Table(resColumnWidths);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            resTable.addCell(new Cell().add(new Paragraph("Room Type").setBold()));
            resTable.addCell(new Cell().add(new Paragraph(order.getRoomType())));

            resTable.addCell(new Cell().add(new Paragraph("Check-in Date").setBold()));
            resTable.addCell(new Cell().add(new Paragraph(order.getCheckinDate().format(dateFormatter))));

            resTable.addCell(new Cell().add(new Paragraph("Check-out Date").setBold()));
            resTable.addCell(new Cell().add(new Paragraph(order.getCheckoutDate().format(dateFormatter))));

            document.add(resTable);

            // Footer Note
            document.add(new Paragraph("\nThank you for booking with us!")
                    .setItalic()
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30));

            // Add line separator with solid gray line, thickness 1f
            SolidLine solidLine = new SolidLine(1f);
            solidLine.setColor(DeviceGray.GRAY);
            document.add(new LineSeparator(solidLine));

            // Hotel Contact Info
            document.add(new Paragraph("OMNI HOTELS")
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12));

            document.add(new Paragraph("123 Serenity Street, Pune, Maharashtra - 411001")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(11));

            document.add(new Paragraph("Phone: +91-9876543210 | Email: omnihotels@gmail.com")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(11));

            document.add(new Paragraph("Website: www.omnihotels.com")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(11));

            document.close();
            return filePath;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
